package avada.media.myhouse24_admin.service;

import avada.media.myhouse24_admin.model.dto.StaffDTO;
import avada.media.myhouse24_admin.model.systemSettings.pages.Staff;

import java.util.List;

public interface StaffService {

    void createStaff(Staff staff);

    void updateStaff(Staff staff);

    void deleteStaff(Long id);

    Staff getStaffById(Long id);

    Staff getStaffByEmail(String email);

    List<Staff> getAllStaff();

    List<StaffDTO> getAllStaffDTOExceptInactive();

}
