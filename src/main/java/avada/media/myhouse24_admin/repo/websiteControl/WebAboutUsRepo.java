package avada.media.myhouse24_admin.repo.websiteControl;

import avada.media.myhouse24_admin.model.websiteControl.pages.WebAboutUs;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebAboutUsRepo extends JpaRepository<WebAboutUs, Long> {

    @EntityGraph(attributePaths = {"gallery", "webExtraInformation.gallery", "documents", "seo"})
    WebAboutUs findFullWebAboutUsById(Long id);

}
