package avada.media.myhouse24_admin.repo.websiteControl;

import avada.media.myhouse24_admin.model.websiteControl.pages.AboutUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutUsRepo extends JpaRepository<AboutUs, Long> {
}
