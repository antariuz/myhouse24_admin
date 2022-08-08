package avada.media.myhouse24_admin.repo;

import avada.media.myhouse24_admin.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
public interface StaffRepo extends JpaRepository<Staff, Long> {
    Staff getStaffByEmail(String email);

}
