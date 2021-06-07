package course.project.springbackend.Service;

import course.project.springbackend.Repositories.SongRepository;
import course.project.springbackend.SpringBackendApplication;
import course.project.springbackend.models.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBackendApplication.class)
class SongServiceTesting {

    @TestConfiguration
    static class SongServiceTest {

        @Bean
        public SongService songService() {
            return new SongService();
        }
    }
    @Autowired
    private SongService songService;

    @MockBean
    private SongRepository songRepository;

    // write test cases here

    @BeforeEach
    public void setUp() {
        Song testSong1 = new Song(1L,"Test1");
        Song testSong2 = new Song(2L,"Test2");

        List<Song> songs=new ArrayList<>();
        songs.add(testSong1);
        songs.add(testSong2);
        List<Song>songSearchList = new ArrayList<>();
        songSearchList.add(testSong2);
        Mockito.when(songRepository.findAll()).thenReturn(songs);
        Mockito.when(songRepository.findById(1L)).thenReturn(Optional.of(testSong1));
        Mockito.when(songRepository.save(Mockito.any(Song.class))).thenAnswer(i -> i.getArguments()[0]);
        Song alex = new Song(100L,"alex");
        songRepository.save(alex);
        Mockito.when(songRepository.findByName(alex.getName()))
                .thenReturn(alex);
    }
    @Test
    void listSongs() {
        Song testSong1 = new Song((long) 100,"Test1");
        Song testSong2 = new Song((long) 101,"Test2");
        List<Song>songs= new ArrayList<>();
        songs.add(testSong1);
        songs.add(testSong2);
        Mockito.when(songRepository.findAll())
                .thenReturn(songs);
        List<Song> foundSongs = songService.listSongs();
        verifyFindAllEmployeesIsCalledOnce();
        assertThat(songs)
                .isEqualTo(foundSongs);
    }

    @Test
    void saveSong() {
        songService.saveSong(new Song(100L,"alex"));
        Song test = songRepository.findByName("alex");
        assertThat(test.getName()).isEqualTo("alex");
    }

    @Test
    void getSong() {
        String found = songService.getSong(1L).getName();
        Song song1 = new Song(1L,"Test1");
        assertThat(found).isEqualTo(song1.getName());
    }

    @Test
    void deleteSong() {
        songRepository.save(new Song(200L,"testDeleteSong"));
        songService.deleteSong(200L);
        Song fromDb = songRepository.findByName("testDeleteSong");
        assertThat(fromDb).isNull();
    }

    @Test
    public void whenInValidName_thenEmployeeShouldNotBeFound() {
        Song fromDb = songService.getSongByName("wrong_name");
        assertThat(fromDb).isNull();
        verifyFindByNameIsCalledOnce("wrong_name");
    }

    @Test
    public void whenValidName_thenEmployeeShouldBeFound() {
        String name = "alex";
        songRepository.save(new Song(104L,"alex"));
        Song found = songService.getSongByName(name);
        assertThat(found.getName()).isEqualTo(name);
    }

    @Test
    void replaceSong() {
        Song replacingSong = new Song(1L,"AfterReplaceName");
        Song test = songService.replaceSong(replacingSong, 1L);
        String replace = replacingSong.getName();
        String tes = test.getName();
        assertThat(replace).isEqualTo(tes);
    }

    private void verifyFindAllEmployeesIsCalledOnce() {
        Mockito.verify(songRepository, VerificationModeFactory.times(1)).findAll();
        Mockito.reset(songRepository);
    }
    private void verifyFindByNameIsCalledOnce(String name) {
        Mockito.verify(songRepository, VerificationModeFactory.times(1)).findByName(name);
        Mockito.reset(songRepository);
    }
}