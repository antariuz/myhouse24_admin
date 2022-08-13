package avada.media.myhouse24_admin.repo.websiteRepo;

import avada.media.myhouse24_admin.model.websiteModels.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactsRepository extends JpaRepository<Contacts, Long> {
}
