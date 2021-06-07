package course.project.springbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "albums")
public class Album {

    @Id
    @Column(name = "albumID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "album_name")
    private String name;

    //Multiplicities

    @JsonIgnore
    @OneToMany(
            mappedBy = "album",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Song> albumSongs = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "artistID")
    private Artist artist;


    public Album(Long id, String name) {
        this.id = id;
        this.name = name;
        this.albumSongs = null;
        this.artist = null;
    }

    public Album() {

    }
    //Methods

    public Long getId() {
        return id;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setAlbumSongs(List<Song> album_songs) {
        this.albumSongs = album_songs;
    }

    public List<Song> getAlbumSongs() {
        return albumSongs;
    }

    public void setId(Long albumID) {
        this.id = albumID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
