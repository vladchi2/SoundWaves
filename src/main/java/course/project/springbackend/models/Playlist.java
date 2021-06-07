package course.project.springbackend.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="playlists")
public class Playlist {

    @Id
    @Column(name = "playlistID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "playlist_name")
    private String name;

    //MULTIPLICITIES

    @OneToMany(
            mappedBy = "playlist",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Song> playlistSongs = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "userid")
    private UserModel user;

    public Playlist(Long id, String name)
    {
        this.id = id;
        this.name = name;
        this.playlistSongs = null;
        this.user = null;
    }
    public Playlist()
    {
        
    }
    //METHODS

    public Long getId() {
        return id;
    }

    public void setId(Long playlistID) {
        this.id = playlistID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Song> getPlaylistSongs() {
        return playlistSongs;
    }

    public void setPlaylistSongs(List<Song> playlistSongs) {
        this.playlistSongs = playlistSongs;
    }

}
