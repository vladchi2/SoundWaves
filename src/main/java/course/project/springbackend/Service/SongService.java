package course.project.springbackend.Service;
import course.project.springbackend.Repositories.SongRepository;
import course.project.springbackend.models.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SongService {
    @Autowired
    private SongRepository songRepository;

    public List<Song> listSongs(){
        return songRepository.findAll();
    }

    public void saveSong(Song song){
        songRepository.save(song);
    }

    public Song getSong(Long songID){
        if (songRepository.findById(songID).isPresent()) {
            return songRepository.findById(songID).get();
        }
        return null;
    }

    public void deleteSong(Long songID){ songRepository.deleteById(songID); }

    public Song getSongByName(String name) {
        return songRepository.findByName(name);
    }

    public Song replaceSong(@RequestBody Song newSong, Long id) {

        return songRepository.findById(id)
                .map(song -> {
                    song.setName(newSong.getName());
                    song.setAlbum(newSong.getAlbum());
                    song.setPlaylist(newSong.getPlaylist());

                    return songRepository.save(song);
                })
                .orElseGet(() -> {
                    newSong.setId(id);
                    return songRepository.save(newSong);
                });


    }

}
