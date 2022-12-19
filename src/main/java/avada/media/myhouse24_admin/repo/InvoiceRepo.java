package avada.media.myhouse24_admin.repo;

import avada.media.myhouse24_admin.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepo extends JpaRepository<Invoice, Long>, JpaSpecificationExecutor<Invoice> {

    @Query("SELECT MAX(id) FROM Invoice")
    Long getLastId();

    boolean existsByUniqueNumber(String uniqueNumber);

}
