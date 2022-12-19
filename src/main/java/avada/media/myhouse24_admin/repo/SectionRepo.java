package avada.media.myhouse24_admin.repo;

import avada.media.myhouse24_admin.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepo extends JpaRepository<Section, Long> {
}
