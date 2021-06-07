package course.project.springbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

@Entity
@Table(name ="songs")
public class Song {
    @Id
    @Column(name = "songID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "song_name")
    private String name;

    //MULTIPLICITIES

    @ManyToOne
    @JoinColumn(name = "albumID")
    private Album album;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "playlistID")
    private Playlist playlist;

    //Constructors

    public Song(Long id, String name) {
        this.id = id;
        this.name = name;
        this.album = null;
        this.playlist = null;
    }

    public Song() {

    }

    //METHODS
    public Long getId() {
        return id;
    }

    public void setId(Long songID) {
        this.id = songID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

}