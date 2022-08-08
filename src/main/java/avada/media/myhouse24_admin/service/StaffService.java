package avada.media.myhouse24_admin.service;

import avada.media.myhouse24_admin.model.Staff;

import java.util.List;

public interface StaffService {

    void createStaff(Staff staff);

    void updateStaff(Staff staff);

    void deleteStaff(Long id);

    Staff getStaffById(Long id);

    Staff getStaffByEmail(String email);

    List<Staff> getAllStaffs();

}
