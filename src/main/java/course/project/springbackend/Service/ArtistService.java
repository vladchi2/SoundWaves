package course.project.springbackend.Service;

import course.project.springbackend.Repositories.AlbumRepository;
import course.project.springbackend.Repositories.ArtistRepository;
import course.project.springbackend.models.Album;
import course.project.springbackend.models.Artist;
import course.project.springbackend.models.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    public List<Artist> listArtists(){
        return artistRepository.findAll();
    }

    public void saveArtist(Artist artist){
        artistRepository.save(artist);
    }

    public Artist getArtist(Long artistID){
        if(artistRepository.findById(artistID).isPresent()) {
            return artistRepository.findById(artistID).get();
        }
        return null;
    }

    public Artist getArtistByName(String name) {
        return artistRepository.findByName(name);
    }

    public void deleteArtist(Long artistID){ artistRepository.deleteById(artistID); }

    public Artist replaceArtist(@RequestBody Artist newArtist, long id) {

        return artistRepository.findById(id)
                .map(artist -> {
                    artist.setName(newArtist.getName());
//                    artist.setArtistAlbums(newArtist.getArtistAlbums() );
                    return artistRepository.save(artist);
                })
                .orElseGet(() -> {
                    newArtist.setId(id);
                    return artistRepository.save(newArtist);
                });
    }
}
