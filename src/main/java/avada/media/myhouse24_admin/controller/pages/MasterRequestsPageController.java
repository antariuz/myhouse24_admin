package avada.media.myhouse24_admin.controller.pages;

import avada.media.myhouse24_admin.model.Flat;
import avada.media.myhouse24_admin.model.MasterRequest;
import avada.media.myhouse24_admin.model.User;
import avada.media.myhouse24_admin.model.dto.*;
import avada.media.myhouse24_admin.model.request.FlatRequest;
import avada.media.myhouse24_admin.model.request.MasterRequestRequest;
import avada.media.myhouse24_admin.model.request.SelectResponse;
import avada.media.myhouse24_admin.model.systemSettings.pages.Role;
import avada.media.myhouse24_admin.model.systemSettings.pages.Staff;
import avada.media.myhouse24_admin.repo.*;
import avada.media.myhouse24_admin.service.MasterRequestService;
import avada.media.myhouse24_admin.spec.FlatSpec;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static avada.media.myhouse24_admin.model.request.SelectResponse.Result;

@Controller
@RequiredArgsConstructor
@RequestMapping("master-requests")
public class MasterRequestsPageController {

    private final MasterRequestService masterRequestService;
    private final MasterRequestRepo masterRequestRepo;
    private final UserRepo userRepo;
    private final FlatRepo flatRepo;
    private final RoleRepo roleRepo;
    private final StaffRepo staffRepo;
    private final FlatSpec flatSpec;

    @GetMapping({"/", ""})
    public ModelAndView showMasterRequestsPage() {
        return new ModelAndView("pages/master-requests");
    }

    @GetMapping("get-all-master-requests")
    public @ResponseBody ResponseByPage<MasterRequestDTO> getAllMasterRequests(MasterRequestRequest masterRequestRequest) {
        return masterRequestService.getAllMasterRequests(masterRequestRequest);
    }

    @GetMapping("search-flat")
    public @ResponseBody SelectResponse searchForFlat(@RequestParam String query,
                                                      @RequestParam Integer page) {
        FlatRequest flatRequest = new FlatRequest();
        flatRequest.setNumber(query);
        flatRequest.setPageIndex(page);
        flatRequest.setPageSize(10);
        Page<Flat> flats = flatRepo.findAll(flatSpec.getFlats(flatRequest), PageRequest.of(flatRequest.getPageIndex() - 1, flatRequest.getPageSize()));
        SelectResponse selectResponse = new SelectResponse();
        if (!flats.getContent().isEmpty()) {
            List<Result> resultsList = selectResponse.getResults();
            for (Flat flat : flats) {
                Result result = new Result();
                result.setId(flat.getId());
                result.setText("№" + flat.getNumber() + ", " + flat.getBuilding().getTitle());
                resultsList.add(result);
            }
            selectResponse.setResults(resultsList);
        }
        selectResponse.setItemsCount(flats.getTotalElements());
        return selectResponse;
    }

    @PostMapping("save")
    public ResponseEntity<Void> saveMasterRequest(@RequestBody MasterRequestDTO masterRequestDTO) {
        masterRequestService.saveMasterRequest(masterRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/update")
    public ResponseEntity<Void> updateMasterRequest(@PathVariable Long id,
                                                    @RequestBody MasterRequestDTO masterRequestDTO) {
        masterRequestService.updateMasterRequest(id, masterRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<Void> deleteMasterRequestById(@PathVariable Long id) {
        masterRequestRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("get-all-users")
    @Transactional
    public @ResponseBody List<UserDTO> getAllUsers() {
        List<User> users = userRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
        if (!users.isEmpty()) {
            List<UserDTO> userDTOList = new ArrayList<>();
            for (User user : users) {
                Hibernate.initialize(user.getProfile());
                UserDTO userDTO = new UserDTO();
                userDTO.setId(user.getId());
                userDTO.setFullName(user.getProfile().getLastname() + " " + user.getProfile().getFirstname() + " " + user.getProfile().getMiddleName());
                userDTOList.add(userDTO);
            }
            return userDTOList;
        } else return new ArrayList<>();
    }

    @GetMapping("get-all-flats")
    public @ResponseBody List<FlatDTO> getAllFlats() {
        List<Flat> flats = flatRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
        if (!flats.isEmpty()) {
            List<FlatDTO> flatDTOS = new ArrayList<>();
            for (Flat flat : flats) {
                FlatDTO flatDTO = new FlatDTO();
                flatDTO.setId(flat.getId());
                flatDTO.setTitle(flat.getNumber(), flat.getBuilding().getTitle());
                flatDTOS.add(flatDTO);
            }
            return flatDTOS;
        } else return new ArrayList<>();
    }

    @GetMapping("get-all-staff-roles")
    public @ResponseBody List<RoleDTO> getMasterRoles() {
        Set<Role> roles = roleRepo.getRolesByNameNotLikeAndNameNotLike("ROLE_DIRECTOR", "ROLE_MANAGER", Sort.by(Sort.Direction.ASC, "id"));
        if (!roles.isEmpty()) {
            List<RoleDTO> roleDTOS = new ArrayList<>();
            for (Role role : roles) {
                RoleDTO roleDTO = new RoleDTO();
                roleDTO.setId(role.getId());
                roleDTO.setTitle(role.getTitle());
                roleDTOS.add(roleDTO);
            }
            return roleDTOS;
        } else return new ArrayList<>();
    }

    @GetMapping("get-all-status")
    public @ResponseBody List<StatusDTO> getAllStatus() {
        List<StatusDTO> statusDTOS = new ArrayList<>();
        for (MasterRequest.Status status : MasterRequest.Status.values()) {
            StatusDTO statusDTO = new StatusDTO();
            statusDTO.setValue(status.name());
            statusDTO.setTitle(status.getTitle());
            statusDTOS.add(statusDTO);
        }
        return statusDTOS;
    }

    @GetMapping("get-all-staff-by-role")
    public @ResponseBody List<StaffDTO> getAllStaffByRole(@RequestParam(required = false) Long id) {
        List<Staff> staffList;
        if (id == null) {
            Role firstRole = roleRepo.getRoleByName("ROLE_DIRECTOR");
            Role secondRole = roleRepo.getRoleByName("ROLE_MANAGER");
            staffList = staffRepo.findAllByRoleNotAndRoleNot(firstRole, secondRole, Sort.by(Sort.Direction.ASC, "id"));
        } else {
            Role role = roleRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Role not found with ID: " + id));
            staffList = staffRepo.findAllByRole(role, Sort.by(Sort.Direction.ASC, "id"));
        }
        if (!staffList.isEmpty()) {
            List<StaffDTO> staffDTOS = new ArrayList<>();
            for (Staff staff : staffList) {
                StaffDTO staffDTO = new StaffDTO();
                staffDTO.setId(staff.getId());
                staffDTO.setFullName(staff.getLastname(), staff.getFirstname());
                staffDTO.setTitle(staff.getRole().getTitle() + " - " + staffDTO.getFullName());
                staffDTOS.add(staffDTO);
            }
            return staffDTOS;
        } else return new ArrayList<>();
    }

}
