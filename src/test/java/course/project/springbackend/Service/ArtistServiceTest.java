package course.project.springbackend.Service;
import course.project.springbackend.Repositories.ArtistRepository;
import course.project.springbackend.SpringBackendApplication;
import course.project.springbackend.models.Artist;
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
class ArtistServiceTest {

    @TestConfiguration
    static class ArtistServiceTests {

        @Bean
        public ArtistService artistService() {
            return new ArtistService();
        }
    }
    @Autowired
    private ArtistService artistService;

    @MockBean
    private ArtistRepository artistRepository;

    // write test cases here

    @BeforeEach
    public void setUp() {
        Artist testArtist1 = new Artist(1L,"Test1");
        Artist testArtist2 = new Artist(2L,"Test2");

        List<Artist> artists=new ArrayList<>();
        artists.add(testArtist1);
        artists.add(testArtist2);
        List<Artist>artistSearchList = new ArrayList<>();
        artistSearchList.add(testArtist2);
        Mockito.when(artistRepository.findAll()).thenReturn(artists);
        Mockito.when(artistRepository.findById(1L)).thenReturn(Optional.of(testArtist1));
        Mockito.when(artistRepository.save(Mockito.any(Artist.class))).thenAnswer(i -> i.getArguments()[0]);
        Artist alex = new Artist(100L,"alex");
        artistRepository.save(alex);
        Mockito.when(artistRepository.findByName(alex.getName()))
                .thenReturn(alex);
    }

    @Test
    void listAll() {
        Artist testArtist1 = new Artist((long) 100,"Test1");
        Artist testArtist2 = new Artist((long) 101,"Test2");
        List<Artist>artists= new ArrayList<>();
        artists.add(testArtist1);
        artists.add(testArtist2);
        Mockito.when(artistRepository.findAll())
                .thenReturn(artists);
        List<Artist> foundArtists = artistService.listArtists();
        verifyFindAllEmployeesIsCalledOnce();
        assertThat(artists)
                .isEqualTo(foundArtists);
    }

    @Test
    void saveArtist() {
        artistService.saveArtist(new Artist(100L,"alex"));
        Artist test = artistRepository.findByName("alex");
        assertThat(test.getName()).isEqualTo("alex");

    }

    @Test
    void getArtist() {
        String found = artistService.getArtist(1L).getName();
        Artist artist1 = new Artist(1L,"Test1");
        assertThat(found).isEqualTo(artist1.getName());
    }

    @Test
    public void whenInValidName_thenEmployeeShouldNotBeFound() {
        Artist fromDb = artistService.getArtistByName("wrong_name");
        assertThat(fromDb).isNull();
        verifyFindByNameIsCalledOnce("wrong_name");
    }

    @Test
    public void whenValidName_thenEmployeeShouldBeFound() {
        String name = "alex";
        artistRepository.save(new Artist(104L,"alex"));
        Artist found = artistService.getArtistByName(name);
        assertThat(found.getName()).isEqualTo(name);
    }

    @Test
    void deleteArtist() {
        artistRepository.save(new Artist(200L,"testDeleteArtist"));
        artistService.deleteArtist(200L);
        Artist fromDb = artistRepository.findByName("testDeleteArtist");
        assertThat(fromDb).isNull();
    }

    @Test
    void replaceArtist() {
        Artist replacingArtist = new Artist(1L,"AfterReplaceName");
        Artist test = artistService.replaceArtist(replacingArtist, 1L);
        String replace = replacingArtist.getName();
        String tes = test.getName();
        assertThat(replace).isEqualTo(tes);
    }

    private void verifyFindAllEmployeesIsCalledOnce() {
        Mockito.verify(artistRepository, VerificationModeFactory.times(1)).findAll();
        Mockito.reset(artistRepository);
    }
    private void verifyFindByNameIsCalledOnce(String name) {
        Mockito.verify(artistRepository, VerificationModeFactory.times(1)).findByName(name);
        Mockito.reset(artistRepository);
    }
}