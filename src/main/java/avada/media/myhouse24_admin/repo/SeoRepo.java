package avada.media.myhouse24_admin.repo;

import avada.media.myhouse24_admin.model.common.Seo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeoRepo extends JpaRepository<Seo, Long> {
}
