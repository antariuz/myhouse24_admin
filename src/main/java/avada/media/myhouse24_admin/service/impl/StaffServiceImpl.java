package avada.media.myhouse24_admin.service.impl;


import avada.media.myhouse24_admin.model.dto.RoleDTO;
import avada.media.myhouse24_admin.model.dto.StaffDTO;
import avada.media.myhouse24_admin.model.systemSettings.pages.Staff;
import avada.media.myhouse24_admin.repo.StaffRepo;
import avada.media.myhouse24_admin.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService, UserDetailsService {

    private final StaffRepo staffRepo;

    @Override
    public void createStaff(Staff staff) {
        staffRepo.save(staff);
    }

    @Override
    public void updateStaff(Staff staff) {
        staffRepo.save(staff);
    }

    @Override
    public void deleteStaff(Long id) {
        staffRepo.deleteById(id);
    }

    @Override
    public Staff getStaffById(Long id) {
        return staffRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Staff not found with id: " + id)
        );
    }

    @Override
    public Staff getStaffByEmail(String email) {
        return staffRepo.getStaffByEmail(email);
    }

    @Override
    public List<Staff> getAllStaff() {
        return staffRepo.findAll();
    }

    @Override
    public List<StaffDTO> getAllStaffDTOExceptInactive() {
        List<Staff> staffList = staffRepo.findAllByStatusNotLike(Staff.Status.INACTIVE, Sort.by(Sort.Direction.ASC, "id"));
        if (!staffList.isEmpty()) {
            List<StaffDTO> staffDTOList = new ArrayList<>();
            for (Staff staff : staffList) {
                StaffDTO staffDTO = new StaffDTO();
                staffDTO.setId(staff.getId());
                staffDTO.setFullName(staff.getLastname(), staff.getFirstname());
                staffDTO.setRole(new RoleDTO(staff.getRole().getId(), staff.getRole().getTitle()));
                staffDTOList.add(staffDTO);
            }
            return staffDTOList;
        }
        return new ArrayList<>();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Staff staff = getStaffByEmail(email);
        if (staff == null) throw new UsernameNotFoundException(String.format("User %s not found", email));
        return new org.springframework.security.core.userdetails.User(staff.getEmail(), staff.getPassword(), staff.isEnabled(), staff.isAccountNonExpired(), staff.isCredentialsNonExpired(), staff.isAccountNonLocked(), staff.getAuthorities());
    }

}
