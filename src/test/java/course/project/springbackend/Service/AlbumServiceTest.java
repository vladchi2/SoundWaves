package course.project.springbackend.Service;

import course.project.springbackend.Repositories.AlbumRepository;
import course.project.springbackend.models.Album;
import org.junit.jupiter.api.Test;
import course.project.springbackend.SpringBackendApplication;
import org.junit.jupiter.api.BeforeEach;
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
class AlbumServiceTest {

    @TestConfiguration
    static class AlbumServiceTests {

        @Bean
        public AlbumService albumService() {
            return new AlbumService();
        }
    }
    @Autowired
    private AlbumService albumService;

    @MockBean
    private AlbumRepository albumRepository;

    @BeforeEach
    public void setUp() {
        Album testAlbum1 = new Album(1L,"Test1");
        Album testAlbum2 = new Album(2L,"Test2");

        List<Album> albums=new ArrayList<>();
        albums.add(testAlbum1);
        albums.add(testAlbum2);
        List<Album>albumSearchList = new ArrayList<>();
        albumSearchList.add(testAlbum2);
        Mockito.when(albumRepository.findAll()).thenReturn(albums);
        Mockito.when(albumRepository.findById(1L)).thenReturn(Optional.of(testAlbum1));
        Mockito.when(albumRepository.save(Mockito.any(Album.class))).thenAnswer(i -> i.getArguments()[0]);
        Album alex = new Album(100L,"alex");
        albumRepository.save(alex);
        Mockito.when(albumRepository.findByName(alex.getName()))
                .thenReturn(alex);
    }
    @Test
    void listAlbums() {
        Album testAlbum1 = new Album((long) 100,"Test1");
        Album testAlbum2 = new Album((long) 101,"Test2");
        List<Album>albums= new ArrayList<>();
        albums.add(testAlbum1);
        albums.add(testAlbum2);

        Mockito.when(albumRepository.findAll())
                .thenReturn(albums);
        List<Album> foundAlbums = albumService.listAlbums();
        verifyFindAllEmployeesIsCalledOnce();
        assertThat(albums)
                .isEqualTo(foundAlbums);
    }

    @Test
    void saveAlbum() {
        albumService.saveAlbum(new Album(100L,"alex"));
        Album test = albumRepository.findByName("alex");

        assertThat(test.getName()).isEqualTo("alex");
    }

    @Test
    void getAlbum() {
        String found = albumService.getAlbum(1L).getName();
        Album album1 = new Album(1L,"Test1");
        assertThat(found).isEqualTo(album1.getName());
    }

    @Test
    void deleteAlbum() {
        albumRepository.save(new Album(200L,"testDeleteAlbum"));
        albumService.deleteAlbum(200L);
        Album fromDb = albumRepository.findByName("testDeleteAlbum");
        assertThat(fromDb).isNull();
    }

    @Test
    void replaceAlbum() {
        Album fromDb = albumService.getAlbumByName("wrong_name");
        assertThat(fromDb).isNull();

        verifyFindByNameIsCalledOnce("wrong_name");
    }

    @Test
    public void whenValidName_thenEmployeeShouldBeFound() {
        String name = "alex";
        albumRepository.save(new Album(104L,"alex"));
        Album found = albumService.getAlbumByName(name);

        assertThat(found.getName()).isEqualTo(name);
    }

    private void verifyFindAllEmployeesIsCalledOnce() {
        Mockito.verify(albumRepository, VerificationModeFactory.times(1)).findAll();
        Mockito.reset(albumRepository);
    }
    private void verifyFindByNameIsCalledOnce(String name) {
        Mockito.verify(albumRepository, VerificationModeFactory.times(1)).findByName(name);
        Mockito.reset(albumRepository);
    }
}