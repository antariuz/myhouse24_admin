package avada.media.myhouse24_admin.repo.website;

import avada.media.myhouse24_admin.model.website.extra.WebService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebServiceRepo extends JpaRepository<WebService, Long> {
}
