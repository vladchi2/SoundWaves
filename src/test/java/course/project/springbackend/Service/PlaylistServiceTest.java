package course.project.springbackend.Service;
import course.project.springbackend.Repositories.PlaylistRepository;
import course.project.springbackend.SpringBackendApplication;
import course.project.springbackend.models.Playlist;
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
class PlaylistServiceTest {
    @TestConfiguration
    static class PlaylistServiceTests {

        @Bean
        public PlaylistService playlistService() {
            return new PlaylistService();
        }
    }
    @Autowired
    private PlaylistService playlistService;

    @MockBean
    private PlaylistRepository playlistRepository;

    // write test cases here

    @BeforeEach
    public void setUp() {
        Playlist testPlaylist1 = new Playlist(1L,"Test1");
        Playlist testPlaylist2 = new Playlist(2L,"Test2");

        List<Playlist> playlists=new ArrayList<>();
        playlists.add(testPlaylist1);
        playlists.add(testPlaylist2);
        List<Playlist>playlistSearchList = new ArrayList<>();
        playlistSearchList.add(testPlaylist2);
        Mockito.when(playlistRepository.findAll()).thenReturn(playlists);
        Mockito.when(playlistRepository.findById(1L)).thenReturn(Optional.of(testPlaylist1));
        Mockito.when(playlistRepository.save(Mockito.any(Playlist.class))).thenAnswer(i -> i.getArguments()[0]);
        Playlist alex = new Playlist(100L,"alex");
        playlistRepository.save(alex);
        Mockito.when(playlistRepository.findByName(alex.getName()))
                .thenReturn(alex);
    }
    @Test
    void listAll() {
        Playlist testPlaylist1 = new Playlist((long) 100,"Test1");
        Playlist testPlaylist2 = new Playlist((long) 101,"Test2");
        List<Playlist>playlists= new ArrayList<>();
        playlists.add(testPlaylist1);
        playlists.add(testPlaylist2);
        Mockito.when(playlistRepository.findAll())
                .thenReturn(playlists);
        List<Playlist> foundPlaylists = playlistService.listPlaylists();
        verifyFindAllEmployeesIsCalledOnce();
        assertThat(playlists)
                .isEqualTo(foundPlaylists);
    }

    @Test
    void savePlaylist() {
        playlistService.savePlaylist(new Playlist(100L,"alex"));
        Playlist test = playlistRepository.findByName("alex");
        assertThat(test.getName()).isEqualTo("alex");
    }

    @Test
    void getPlaylist() {
        String found = playlistService.getPlaylist(1L).getName();
        Playlist playlist1 = new Playlist(1L,"Test1");
        assertThat(found).isEqualTo(playlist1.getName());
    }

    @Test
    void deletePlaylist() {
        playlistRepository.save(new Playlist(200L,"testDeletePlaylist"));
        playlistService.deletePlaylist(200L);
        Playlist fromDb = playlistRepository.findByName("testDeletePlaylist");
        assertThat(fromDb).isNull();
    }
    @Test
    public void whenInValidName_thenEmployeeShouldNotBeFound() {
        Playlist fromDb = playlistService.getPlaylistByName("wrong_name");
        assertThat(fromDb).isNull();
        verifyFindByNameIsCalledOnce("wrong_name");
    }

    @Test
    public void whenValidName_thenEmployeeShouldBeFound() {
        String name = "alex";
        playlistRepository.save(new Playlist(104L,"alex"));
        Playlist found = playlistService.getPlaylistByName(name);
        assertThat(found.getName()).isEqualTo(name);
    }

    @Test
    void replacePlaylist() {
        Playlist replacingPlaylist = new Playlist(1L,"AfterReplaceName");
        Playlist test = playlistService.replacePlaylist(replacingPlaylist, 1L);
        String replace = replacingPlaylist.getName();
        String tes = test.getName();
        assertThat(replace).isEqualTo(tes);
    }
    private void verifyFindAllEmployeesIsCalledOnce() {
        Mockito.verify(playlistRepository, VerificationModeFactory.times(1)).findAll();
        Mockito.reset(playlistRepository);
    }
    private void verifyFindByNameIsCalledOnce(String name) {
        Mockito.verify(playlistRepository, VerificationModeFactory.times(1)).findByName(name);
        Mockito.reset(playlistRepository);
    }
}