package avada.media.myhouse24_admin.repo.systemSettings;

import avada.media.myhouse24_admin.model.systemSettings.pages.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDetailsRepo extends JpaRepository<PaymentDetails, Long> {
}
