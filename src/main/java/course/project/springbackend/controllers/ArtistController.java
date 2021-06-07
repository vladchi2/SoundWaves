package course.project.springbackend.controllers;

import course.project.springbackend.Service.ArtistService;
import course.project.springbackend.models.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("artists")
@CrossOrigin(origins = "http://localhost:3000")
public class ArtistController {

    @Autowired
    ArtistService artistService;

    @GetMapping("")
    public List<Artist> getAllArtists(){
        return artistService.listArtists();
    }

    @PostMapping("")
    public Artist addArtist(@RequestBody Artist artist) {
        artistService.saveArtist(artist);
        return artist;
    }

    @DeleteMapping(value = "/{id}")
    public long deleteArtist(@PathVariable("id") long ID){
        artistService.deleteArtist(ID);
        return ID;
    }

    @GetMapping(value = "/{id}")
    public Artist showArtist(@PathVariable("id") long ID){
        return artistService.getArtist(ID);
    }

    @PutMapping(path = "/{id}")
    public Artist replaceArtist(@RequestBody Artist newArtist, @PathVariable long id) {

        return artistService.replaceArtist(newArtist,id);
    }
}
