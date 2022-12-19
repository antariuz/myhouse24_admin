package avada.media.myhouse24_admin.controller.pages.systemSettings;

import avada.media.myhouse24_admin.model.dto.StaffDTO;
import avada.media.myhouse24_admin.model.systemSettings.pages.Staff;
import avada.media.myhouse24_admin.repo.RoleRepo;
import avada.media.myhouse24_admin.repo.StaffRepo;
import avada.media.myhouse24_admin.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("system-settings/staff")
@RequiredArgsConstructor
@Slf4j
public class StaffPageController {

    private final StaffService staffService;
    private final StaffRepo staffRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @RequestMapping({"/", ""})
    public ModelAndView showStaffPage() {
        return new ModelAndView("pages/system-settings/staff");
    }

    @GetMapping("get-current-staff-id")
    public @ResponseBody Long getCurrentStaffID(Principal principal) {
        return staffRepo.getStaffByEmail(principal.getName()).getId();
    }

    @GetMapping("get-all-staff")
    public @ResponseBody List<StaffDTO> getAllStaffDTOExceptInactive() {
        return staffService.getAllStaffDTOExceptInactive();
    }

    @GetMapping("get-all")
    public @ResponseBody List<Staff> getAllStaff() {
        return staffRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @GetMapping("{id}/get-staff")
    public @ResponseBody Staff getStaffById(@PathVariable Long id) {
        return staffRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Staff not found with ID: " + id));
    }

    @PostMapping("add")
    public ResponseEntity<Void> addStaff(@RequestBody Staff staff) {
        staff.setRole(roleRepo.getRoleByName(staff.getRole().getName()));
        staff.setPassword(passwordEncoder.encode(staff.getPassword()));
        staff.setPhoneNumber(staff.getPhoneNumber().replaceAll(" ", ""));
        staffRepo.save(staff);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/update")
    public ResponseEntity<Void> updateStaff(@PathVariable Long id,
                                            @RequestBody Staff staff) {
        staff.setRole(roleRepo.getRoleByName(staff.getRole().getName()));
        if (staff.getPassword() == null || staff.getPassword().equals("")) {
            staff.setPassword(staffRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Staff not found with ID: " + id)).getPassword());
        } else staff.setPassword(passwordEncoder.encode(staff.getPassword()));
        staff.setPhoneNumber(staff.getPhoneNumber().replaceAll(" ", ""));
        staffRepo.save(staff);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        if (id == 1) log.warn("Попытка удалить ROOT администратора");
        else staffRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("check-email")
    @ResponseBody
    public boolean checkEmail(@RequestParam(required = false) Long id, String email) {
        if (id == null) return !staffRepo.existsStaffByEmail(email);
        else if (email.equals(staffRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Staff not found with ID: " + id))
                .getEmail())) return true;
        else return !staffRepo.existsStaffByEmail(email);
    }

}
