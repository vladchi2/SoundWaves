package course.project.springbackend.Service;
import course.project.springbackend.Repositories.AlbumRepository;
import course.project.springbackend.models.Album;
import course.project.springbackend.models.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AlbumService {
    @Autowired
    private AlbumRepository albumRepository;

    public List<Album> listAlbums(){
        return albumRepository.findAll();
    }

    public void saveAlbum(Album album){
        albumRepository.save(album);
    }

    public Album getAlbum(Long albumID){
        if(albumRepository.findById(albumID).isPresent())
        {
            return albumRepository.findById(albumID).get();
        }
        return null;
    }
    public Album getAlbumByName(String name) {
        return albumRepository.findByName(name);
    }

    public void deleteAlbum(Long albumID){ albumRepository.deleteById(albumID); }

    public Album replaceAlbum(@RequestBody Album newAlbum, long id) {

        return albumRepository.findById(id)
                .map(album -> {
                    album.setName(newAlbum.getName());
//                    album.setAlbumSongs(newAlbum.getAlbumSongs());
                    return albumRepository.save(album);
                })
                .orElseGet(() -> {
                    newAlbum.setId(id);
                    return albumRepository.save(newAlbum);
                });
    }
}
