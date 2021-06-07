package course.project.springbackend.controllers;

import course.project.springbackend.Service.AlbumService;
import course.project.springbackend.models.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("albums")
@CrossOrigin(origins = "http://localhost:3000")
public class AlbumController {

    @Autowired
    AlbumService albumService;

    @GetMapping("")
    public List<Album> getAllAlbums(){
        return albumService.listAlbums();
    }

    @PostMapping("")
    public Album addAlbum(@RequestBody Album album) {
        albumService.saveAlbum(album);
        return album;
    }

    @DeleteMapping(value = "/{id}")
    public long deleteAlbum(@PathVariable("id") long albumID){
        albumService.deleteAlbum(albumID);
        return albumID;
    }

    @GetMapping(value = "/{id}")
    public Album showAlbum(@PathVariable("id") long albumID){
        return albumService.getAlbum(albumID);
    }

    @PutMapping(path = "/{id}")
    public Album replaceAlbum(@RequestBody Album newAlbum, @PathVariable long id) {

        return albumService.replaceAlbum(newAlbum,id);
    }
}
