package avada.media.myhouse24_admin.repo;

import avada.media.myhouse24_admin.model.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlatRepo extends JpaRepository<Flat, Long>, JpaSpecificationExecutor<Flat> {

    List<Flat> findAllByUserId(Long id);

    List<Flat> findAllByBuildingId(Long id);

    List<Flat> findAllByBuildingIdAndSectionId(Long buildingId, Long sectionId);

    List<Flat> findAllByBuildingIdAndFloorId(Long buildingId, Long floorId);

    List<Flat> findAllByBuildingIdAndSectionIdAndFloorId(Long buildingId, Long sectionId, Long floorId);

}
