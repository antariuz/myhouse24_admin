package avada.media.myhouse24_admin.repo.systemSettings;

import avada.media.myhouse24_admin.model.systemSettings.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailsRepo extends JpaRepository<PaymentDetails, Long> {
}
