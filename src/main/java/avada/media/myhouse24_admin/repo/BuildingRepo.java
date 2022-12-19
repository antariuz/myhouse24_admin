package avada.media.myhouse24_admin.repo;

import avada.media.myhouse24_admin.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepo extends JpaRepository<Building, Long>, JpaSpecificationExecutor<Building> {
}
