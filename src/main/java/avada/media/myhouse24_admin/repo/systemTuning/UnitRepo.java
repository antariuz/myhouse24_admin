package avada.media.myhouse24_admin.repo.systemTuning;

import avada.media.myhouse24_admin.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepo extends JpaRepository<Unit, Long> {
    Unit findUnitByName(String unit);
}