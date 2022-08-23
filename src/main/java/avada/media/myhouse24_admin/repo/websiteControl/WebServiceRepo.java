package avada.media.myhouse24_admin.repo.websiteControl;

import avada.media.myhouse24_admin.model.websiteControl.extra.WebService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebServiceRepo extends JpaRepository<WebService, Long> {
}
