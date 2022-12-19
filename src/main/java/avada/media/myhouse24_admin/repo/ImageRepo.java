package avada.media.myhouse24_admin.repo;

import avada.media.myhouse24_admin.model.websiteControl.extra.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepo extends JpaRepository<Image, Long> {
}
