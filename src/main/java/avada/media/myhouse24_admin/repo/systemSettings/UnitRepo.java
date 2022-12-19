package avada.media.myhouse24_admin.repo.systemSettings;

import avada.media.myhouse24_admin.model.systemSettings.extra.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepo extends JpaRepository<Unit, Long> {
}