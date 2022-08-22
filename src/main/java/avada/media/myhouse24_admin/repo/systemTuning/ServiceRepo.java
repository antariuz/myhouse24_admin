package avada.media.myhouse24_admin.repo.systemTuning;

import avada.media.myhouse24_admin.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepo extends JpaRepository<Service, Long> {
}