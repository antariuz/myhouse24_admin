package avada.media.myhouse24_admin.repo;

import avada.media.myhouse24_admin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query("SELECT MAX(id) FROM User")
    Long getLastId();

    boolean existsByEmail(String email);

    boolean existsByUniqueId(String uniqueId);

    @Query("SELECT count(id) from User WHERE NOT status = 'INACTIVE'")
    Long countUsersByStatusNotInactive();

    @Query(value = "SELECT balance FROM _user LEFT JOIN Flat on flat.user_id = _user.id" +
            " LEFT JOIN account on flat.id = account.flat_id WHERE _user.id = ?", nativeQuery = true)
    List<Double> getUserBalancesByUserId(Long id);

    @Query("SELECT user FROM User user WHERE user.status = 'NEW'")
    List<User> getNewUsers();

}
