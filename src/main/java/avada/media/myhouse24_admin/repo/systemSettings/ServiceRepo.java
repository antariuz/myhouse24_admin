package avada.media.myhouse24_admin.repo.systemSettings;

import avada.media.myhouse24_admin.model.systemSettings.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepo extends JpaRepository<Service, Long> {
}