package course.project.springbackend.Repositories;

import course.project.springbackend.models.Playlist;
import course.project.springbackend.models.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist,Long> {
    public Playlist findByName(String name);

}
