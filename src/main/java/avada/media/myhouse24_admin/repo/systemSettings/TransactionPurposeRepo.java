package avada.media.myhouse24_admin.repo.systemSettings;

import avada.media.myhouse24_admin.model.systemSettings.TransactionPurpose;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionPurposeRepo extends JpaRepository<TransactionPurpose, Long> {
}