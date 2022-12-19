package avada.media.myhouse24_admin.repo.websiteControl;

import avada.media.myhouse24_admin.model.websiteControl.pages.WebMain;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebMainRepo extends JpaRepository<WebMain, Long> {

    @EntityGraph(attributePaths = {"webNextToUsList", "seo"})
    WebMain findFullWebMainById(Long id);

}
