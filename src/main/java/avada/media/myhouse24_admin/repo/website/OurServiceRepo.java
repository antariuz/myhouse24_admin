package avada.media.myhouse24_admin.repo.website;

import avada.media.myhouse24_admin.model.website.OurService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OurServiceRepo extends JpaRepository<OurService, Long> {
}