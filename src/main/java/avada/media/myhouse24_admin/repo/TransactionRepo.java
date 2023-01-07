package avada.media.myhouse24_admin.repo;

import avada.media.myhouse24_admin.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

    @Query("SELECT MAX(id) FROM Transaction")
    Long getLastId();

    boolean existsByUniqueNumber(String uniqueNumber);

    @Query(value = "SELECT amount FROM Transaction WHERE used AND type = ?", nativeQuery = true)
    List<Double> getAmountsByType(String type);

    List<Transaction> getAllTransactionsByTypeAndRequestedDateAfter(Transaction.Type type, Date date);

}
