package course.project.springbackend.Repositories;

import course.project.springbackend.models.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
    public Song findByName(String name);
}
