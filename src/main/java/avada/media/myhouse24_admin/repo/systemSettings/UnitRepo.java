package avada.media.myhouse24_admin.repo.systemSettings;

import avada.media.myhouse24_admin.model.systemSettings.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepo extends JpaRepository<Unit, Long> {
    Unit findUnitByName(String unit);
}