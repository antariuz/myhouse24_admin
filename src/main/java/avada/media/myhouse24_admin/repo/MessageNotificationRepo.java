package avada.media.myhouse24_admin.repo;

import avada.media.myhouse24_admin.model.MessageNotification;
import avada.media.myhouse24_admin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageNotificationRepo extends JpaRepository<MessageNotification, Long> {

    List<MessageNotification> getAllByWasViewedAndUser(boolean wasViewed, User user);

}
