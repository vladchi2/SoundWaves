package course.project.springbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="artists")
public class Artist {
    @Id
    @Column(name = "artistID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "artist_name")
    private String name;

    //Multiplicities

    @JsonIgnore
    @OneToMany(
            mappedBy = "artist",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Album> artistAlbums = new ArrayList<>();

    public Artist(Long id, String name)
    {
        this.id = id;
        this.name = name;
        this.artistAlbums = null;
    }
    public Artist()
    {

    }

    //Methods

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

    public List<Album> getArtistAlbums() {
        return artistAlbums;
    }

    public void setArtistAlbums(List<Album> artistAlbums) {
        this.artistAlbums = artistAlbums;
    }
}
