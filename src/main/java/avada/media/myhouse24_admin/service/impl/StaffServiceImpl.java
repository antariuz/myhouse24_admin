package avada.media.myhouse24_admin.service.impl;


import avada.media.myhouse24_admin.model.Staff;
import avada.media.myhouse24_admin.model.common.Role;
import avada.media.myhouse24_admin.repo.StaffRepo;
import avada.media.myhouse24_admin.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Staff> getAllStaffs() {
        return staffRepo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Staff staff = getStaffByEmail(email);
        if (staff == null) throw new UsernameNotFoundException(String.format("User %s not found", email));
        return new org.springframework.security.core.userdetails.User(staff.getEmail(), staff.getPassword(), mapRolesToAuthorities(staff.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}
