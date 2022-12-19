package avada.media.myhouse24_admin.repo.websiteControl;

import avada.media.myhouse24_admin.model.websiteControl.pages.WebServices;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebServicesRepo extends JpaRepository<WebServices, Long> {

    @EntityGraph(attributePaths = {"seo"})
    WebServices findWithSeoWebServicesById(Long id);

    @EntityGraph(attributePaths = {"webServiceList"})
    WebServices findWithServiceListWebServicesById(Long id);

}
