package avada.media.myhouse24_admin.repo.website;

import avada.media.myhouse24_admin.model.website.pages.NextToUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NextToUsRepo extends JpaRepository<NextToUs, Long> {
}