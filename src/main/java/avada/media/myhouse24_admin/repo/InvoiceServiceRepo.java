package avada.media.myhouse24_admin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceServiceRepo extends JpaRepository<avada.media.myhouse24_admin.model.InvoiceService, Long> {
}
