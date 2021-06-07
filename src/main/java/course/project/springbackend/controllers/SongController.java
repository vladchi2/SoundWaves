package course.project.springbackend.controllers;

import course.project.springbackend.Service.SongService;
import course.project.springbackend.models.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("songs")
@CrossOrigin(origins = "http://localhost:3000")
public class SongController {

    @Autowired
    SongService songService;

    @GetMapping("")
    public List<Song> getAllSongs(){
        return songService.listSongs();
    }

    @PostMapping("")
    public Song addSong(@RequestBody Song song) {
        songService.saveSong(song);
        return song;
    }

    @DeleteMapping(value = "/{id}")
    public long deleteSong(@PathVariable("id") long songID){
        songService.deleteSong(songID);
        return songID;
    }

    @GetMapping(value = "/{npms}")
    public Song showSong(@PathVariable("id") long songID){
        return songService.getSong(songID);
    }

    @PutMapping(path = "/{id}")
    public Song replaceSong(@RequestBody Song newSong, @PathVariable long id) {

        return songService.replaceSong(newSong,id);
    }


}
