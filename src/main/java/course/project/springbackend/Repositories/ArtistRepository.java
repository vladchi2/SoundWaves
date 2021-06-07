package course.project.springbackend.Repositories;

import course.project.springbackend.models.Artist;
import course.project.springbackend.models.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist,Long> {
    public Artist findByName(String name);

}
