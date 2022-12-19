package avada.media.myhouse24_admin.repo;

import avada.media.myhouse24_admin.model.systemSettings.pages.Role;
import avada.media.myhouse24_admin.model.systemSettings.pages.Staff;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepo extends JpaRepository<Staff, Long> {
    Staff getStaffByEmail(String email);

    boolean existsStaffByEmail(String email);

    List<Staff> findAllByStatusNotLike(Staff.Status status, Sort sort);

    List<Staff> findAllByRoleNotAndRoleNot(Role firstRole, Role secondRole, Sort sort);

    List<Staff> findAllByRole(Role firstRole, Sort sort);

}
