package avada.media.myhouse24_admin.repo.websiteControl;

import avada.media.myhouse24_admin.model.websiteControl.pages.WebFeedback;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebFeedbackRepo extends JpaRepository<WebFeedback, Long> {

    @EntityGraph(attributePaths = {"seo"})
    WebFeedback findWithSeoWebFeedbackById(Long id);

}
