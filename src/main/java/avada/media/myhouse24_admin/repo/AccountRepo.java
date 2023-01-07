package avada.media.myhouse24_admin.repo;

import avada.media.myhouse24_admin.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {

    Account findFirstByOrderByIdDesc();

    boolean existsByUniqueNumber(String uniqueNumberumber);

    Account findByUniqueNumber(String uniqueNumber);

    @Query("SELECT balance FROM Account")
    List<Double> getAmounts();

    @Query("SELECT account FROM Account account WHERE account.flat is NULL")
    List<Account> getAllNotUsedAccounts();

    @Query(value = "SELECT * FROM account " +
            "LEFT JOIN flat on flat.id = account.flat_id " +
            "LEFT JOIN _user on flat.user_id = _user.id " +
            "WHERE _user.id = ? AND account.unique_number LIKE CONCAT(?, '%')", nativeQuery = true)
    Page<Account> getAccountsByUserId(Long id, String uniqueNumber, Pageable pageable);

}
