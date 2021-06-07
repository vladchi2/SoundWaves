package course.project.springbackend.controllers;

import course.project.springbackend.Service.PlaylistService;
import course.project.springbackend.models.Playlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("playlists")
@CrossOrigin(origins = "http://localhost:3000")
public class PlaylistController {

    @Autowired
    PlaylistService playlistService;

    @GetMapping("")
    public List<Playlist> getAllPlaylists(){
        return playlistService.listPlaylists();
    }

    @PostMapping("")
    public Playlist addPlaylist(@RequestBody Playlist playlist) {
        playlistService.savePlaylist(playlist);
        return playlist;
    }

    @DeleteMapping(value = "/{id}")
    public long deletePlaylist(@PathVariable("id") long ID){
        playlistService.deletePlaylist(ID);
        return ID;
    }

    @GetMapping(value = "/{id}")
    public Playlist showPlaylist(@PathVariable("id") long ID){
        return playlistService.getPlaylist(ID);
    }

    @PutMapping(path = "/{id}")
    public Playlist replacePlaylist(@RequestBody Playlist newPlaylist, @PathVariable long id) {

        return playlistService.replacePlaylist(newPlaylist,id);
    }
}
