package avada.media.myhouse24_admin.repo.websiteControl;

import avada.media.myhouse24_admin.model.websiteControl.extra.WebNextToUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebNextToUsRepo extends JpaRepository<WebNextToUs, Long> {
}
