package avada.media.myhouse24_admin.repo.systemSettings;

import avada.media.myhouse24_admin.model.systemSettings.extra.TariffService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffServiceRepo extends JpaRepository<TariffService, Long> {
}
