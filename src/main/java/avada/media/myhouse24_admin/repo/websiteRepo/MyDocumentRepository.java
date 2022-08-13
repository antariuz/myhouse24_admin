package avada.media.myhouse24_admin.repo.websiteRepo;

import avada.media.myhouse24_admin.model.websiteModels.MyDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyDocumentRepository extends JpaRepository<MyDocument, Long> {
}