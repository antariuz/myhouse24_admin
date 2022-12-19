package avada.media.myhouse24_admin.repo.websiteControl;

import avada.media.myhouse24_admin.model.websiteControl.extra.WebExtraInformation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebExtraInformationRepo extends JpaRepository<WebExtraInformation, Long> {

    @EntityGraph(attributePaths = {"gallery"})
    WebExtraInformation findFullWebExtraInformationById(Long id);

}
