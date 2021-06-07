package course.project.springbackend.Repositories;

import course.project.springbackend.models.Album;
import course.project.springbackend.models.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    public Album findByName(String name);

}
