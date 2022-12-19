package avada.media.myhouse24_admin.repo;

import avada.media.myhouse24_admin.model.Counter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CounterRepo extends JpaRepository<Counter, Long>, JpaSpecificationExecutor<Counter> {

    Counter findFirstByOrderByIdDesc();

    boolean existsByUniqueNumber(String uniqueNumber);

}
