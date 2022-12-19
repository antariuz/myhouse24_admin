package avada.media.myhouse24_admin.controller.pages;

import avada.media.myhouse24_admin.model.Account;
import avada.media.myhouse24_admin.model.Building;
import avada.media.myhouse24_admin.model.Flat;
import avada.media.myhouse24_admin.model.User;
import avada.media.myhouse24_admin.model.dto.*;
import avada.media.myhouse24_admin.model.request.AccountRequest;
import avada.media.myhouse24_admin.model.request.SelectResponse;
import avada.media.myhouse24_admin.repo.AccountRepo;
import avada.media.myhouse24_admin.repo.BuildingRepo;
import avada.media.myhouse24_admin.repo.FlatRepo;
import avada.media.myhouse24_admin.repo.UserRepo;
import avada.media.myhouse24_admin.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("accounts")
public class AccountsPageController {

    private final AccountService accountService;
    private final FlatRepo flatRepo;
    private final UserRepo userRepo;
    private final BuildingRepo buildingRepo;
    private final AccountRepo accountRepo;

    @GetMapping({"/", ""})
    public ModelAndView showAccountsPage() {
        return new ModelAndView("pages/accounts");
    }

    @GetMapping("get-all-accounts")
    public @ResponseBody ResponseByPage<AccountDTO> getAllFlats(AccountRequest accountRequest) {
        return accountService.getAllAccounts(accountRequest);
    }

    @GetMapping("get-all-status")
    public @ResponseBody List<StatusDTO> getAllStatusDTO() {
        if (Account.Status.values().length > 0) {
            List<StatusDTO> statusDTOS = new ArrayList<>();
            for (Account.Status status : Account.Status.values()) {
                statusDTOS.add(new StatusDTO(status.name(), status.getTitle()));
            }
            return statusDTOS;
        } else return new ArrayList<>();
    }

    @GetMapping("get-all-buildings")
    @Transactional
    public @ResponseBody List<BuildingDTO> getAllBuildingsDTO() {
        List<Building> buildings = buildingRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
        if (!buildings.isEmpty()) {
            List<BuildingDTO> buildingDTOS = new ArrayList<>();
            for (Building building : buildings) {
                Hibernate.initialize(building.getSections());
                BuildingDTO buildingDTO = new BuildingDTO();
                buildingDTO.setId(building.getId());
                buildingDTO.setTitle(building.getTitle());
                buildingDTO.setSections(building.getSections());
                buildingDTOS.add(buildingDTO);
            }
            return buildingDTOS;
        } else return new ArrayList<>();
    }

    @GetMapping("get-new-unique-number")
    public @ResponseBody String getNewUniqueNumber() {
        return accountService.getNewUniqueNumber();
    }

    @GetMapping("get-flats-of-building")
    @Transactional
    public @ResponseBody List<FlatDTO> getAllFlatsDTO(Long buildingId,
                                                      @RequestParam(required = false) Long sectionId,
                                                      @RequestParam(required = false) Long floorId) {
        if (sectionId == null) {
            List<Flat> flats = flatRepo.findAllByBuildingId(buildingId);
            if (!flats.isEmpty()) {
                List<FlatDTO> flatDTOS = new ArrayList<>();
                for (Flat flat : flats) {
                    FlatDTO flatDTO = new FlatDTO();
                    flatDTO.setId(flat.getId());
                    flatDTO.setNumber("№" + flat.getNumber());
                    if (flat.getUser() != null) {
                        Hibernate.initialize(flat.getUser().getProfile());
                        UserDTO userDTO = new UserDTO();
                        userDTO.setId(flat.getUser().getId());
                        userDTO.setFullName(flat.getUser().getProfile().getLastname(), flat.getUser().getProfile().getFirstname(), flat.getUser().getProfile().getMiddleName());
                        userDTO.setPhoneNumber(flat.getUser().getProfile().getPhoneNumber());
                        flatDTO.setUser(userDTO);
                    }
                    flatDTOS.add(flatDTO);
                }
                return flatDTOS;
            } else return new ArrayList<>();
        } else {
            List<Flat> flats = flatRepo.findAllByBuildingIdAndSectionId(buildingId, sectionId);
            if (!flats.isEmpty()) {
                List<FlatDTO> flatDTOS = new ArrayList<>();
                for (Flat flat : flats) {
                    FlatDTO flatDTO = new FlatDTO();
                    flatDTO.setId(flat.getId());
                    flatDTO.setTitle(flat.getNumber(), flat.getBuilding().getTitle());
                    if (flat.getUser() != null) {
                        Hibernate.initialize(flat.getUser().getProfile());
                        UserDTO userDTO = new UserDTO();
                        userDTO.setId(flat.getUser().getId());
                        userDTO.setFullName(flat.getUser().getProfile().getLastname(), flat.getUser().getProfile().getFirstname(), flat.getUser().getProfile().getMiddleName());
                        userDTO.setPhoneNumber(flat.getUser().getProfile().getPhoneNumber());
                        flatDTO.setUser(userDTO);
                    }
                    flatDTOS.add(flatDTO);
                }
                return flatDTOS;
            } else return new ArrayList<>();
        }
    }

    @GetMapping("get-all-users")
    @Transactional
    public @ResponseBody List<UserDTO> getAllUsersDTO() {
        List<User> users = userRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
        if (!users.isEmpty()) {
            List<UserDTO> userDTOList = new ArrayList<>();
            for (User user : users) {
                Hibernate.initialize(user.getProfile());
                UserDTO userDTO = new UserDTO();
                userDTO.setId(user.getId());
                userDTO.setFullName(user.getProfile().getLastname(), user.getProfile().getFirstname(), user.getProfile().getMiddleName());
                userDTO.setPhoneNumber(user.getProfile().getPhoneNumber());
                userDTOList.add(userDTO);
            }
            return userDTOList;
        } else return new ArrayList<>();
    }

    @PostMapping("is-unique-number-not-exists")
    public @ResponseBody boolean isUniqueNumberNotExists(@RequestParam(required = false) Long id, String number) {
        return accountService.isUniqueNumberNotExists(id, number);
    }

    @GetMapping("search-account")
    public @ResponseBody SelectResponse searchForUser(@RequestParam String query,
                                                      @RequestParam Integer page) {
        return accountService.searchForAccount(query, page);
    }

    @PostMapping("save")
    public ResponseEntity<Void> saveAccount(@RequestBody AccountDTO accountDTO) {
        accountService.saveAccount(accountDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/update")
    public ResponseEntity<Void> updateAccount(@PathVariable Long id,
                                              @RequestBody AccountDTO accountDTO) {
        accountService.updateAccount(id, accountDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        accountRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
