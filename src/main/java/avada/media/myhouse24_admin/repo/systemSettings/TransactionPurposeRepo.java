package avada.media.myhouse24_admin.repo.systemSettings;

import avada.media.myhouse24_admin.model.systemSettings.pages.TransactionPurpose;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionPurposeRepo extends JpaRepository<TransactionPurpose, Long> {

    List<TransactionPurpose> getTransactionPurposesByType(TransactionPurpose.Type type, Sort sort);

}
