package avada.media.myhouse24_admin.repo;

import avada.media.myhouse24_admin.model.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloorRepo extends JpaRepository<Floor, Long> {
}
