package course.project.springbackend.Service;

import course.project.springbackend.Repositories.PlaylistRepository;
import course.project.springbackend.Repositories.SongRepository;
import course.project.springbackend.models.Playlist;
import course.project.springbackend.models.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    public List<Playlist> listPlaylists(){
        return playlistRepository.findAll();
    }

    public void savePlaylist(Playlist playlist){
        playlistRepository.save(playlist);
    }

    public Playlist getPlaylist(Long playlistID){
        if(playlistRepository.findById(playlistID).isPresent()) {
            return playlistRepository.findById(playlistID).get();
        }
        return null;
    }
    public Playlist getPlaylistByName(String name) {
        return playlistRepository.findByName(name);
    }

    public void deletePlaylist(Long playlistID){ playlistRepository.deleteById(playlistID); }

    public Playlist replacePlaylist(@RequestBody Playlist newPlaylist, long id) {

        return playlistRepository.findById(id)
                .map(playlist -> {
                    playlist.setName(newPlaylist.getName());
//                    playlist.setPlaylistSongs(newPlaylist.getPlaylistSongs());

                    return playlistRepository.save(playlist);
                })
                .orElseGet(() -> {
                    newPlaylist.setId(id);
                    return playlistRepository.save(newPlaylist);
                });
    }
}
