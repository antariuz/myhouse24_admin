package avada.media.myhouse24_admin.controller.pages;

import avada.media.myhouse24_admin.model.Building;
import avada.media.myhouse24_admin.model.Floor;
import avada.media.myhouse24_admin.model.Section;
import avada.media.myhouse24_admin.model.User;
import avada.media.myhouse24_admin.model.dto.AccountDTO;
import avada.media.myhouse24_admin.model.dto.BuildingDTO;
import avada.media.myhouse24_admin.model.dto.FlatDTO;
import avada.media.myhouse24_admin.model.dto.UserDTO;
import avada.media.myhouse24_admin.model.request.FlatRequest;
import avada.media.myhouse24_admin.model.response.ResponseByPage;
import avada.media.myhouse24_admin.repo.*;
import avada.media.myhouse24_admin.service.AccountService;
import avada.media.myhouse24_admin.service.FlatService;
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
import java.util.Set;
import java.util.TreeSet;

@Controller
@RequiredArgsConstructor
@RequestMapping("flats")
public class FlatsPageController {

    private final FlatService flatService;
    private final FlatRepo flatRepo;
    private final UserRepo userRepo;
    private final BuildingRepo buildingRepo;
    private final SectionRepo sectionRepo;
    private final FloorRepo floorRepo;

    private final AccountService accountService;

    @GetMapping({"/", ""})
    public ModelAndView showFlatsPage() {
        return new ModelAndView("pages/flats");
    }

    @GetMapping("get-all-flats")
    public @ResponseBody ResponseByPage<FlatDTO> getAllFlats(FlatRequest flatRequest) {
        return flatService.getAllFlats(flatRequest);
    }

    @GetMapping("get-all-buildings")
    @Transactional
    public @ResponseBody List<BuildingDTO> getAllBuildings() {
        List<Building> buildings = buildingRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
        if (!buildings.isEmpty()) {
            List<BuildingDTO> buildingDTOS = new ArrayList<>();
            for (Building building : buildings) {
                Hibernate.initialize(building.getSections());
                Hibernate.initialize(building.getFloors());
                BuildingDTO buildingDTO = new BuildingDTO();
                buildingDTO.setId(building.getId());
                buildingDTO.setTitle(building.getTitle());
                buildingDTO.setSections(building.getSections());
                buildingDTO.setFloors(building.getFloors());
                buildingDTOS.add(buildingDTO);
            }
            return buildingDTOS;
        } else return new ArrayList<>();
    }

    @GetMapping("get-all-not-used-accounts")
    public @ResponseBody List<AccountDTO> getAllAccounts() {
        return accountService.getAllNotUsedAccounts();
    }

    @GetMapping("get-all-sections")
    public @ResponseBody Set<String> getAllSections() {
        List<Section> sections = sectionRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
        if (!sections.isEmpty()) {
            Set<String> sectionSet = new TreeSet<>();
            for (Section section : sections) {
                sectionSet.add(section.getName());
            }
            return sectionSet;
        } else return new TreeSet<>();
    }

    @GetMapping("get-all-floors")
    public @ResponseBody Set<String> getAllFloors() {
        List<Floor> floors = floorRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
        if (!floors.isEmpty()) {
            Set<String> floorSet = new TreeSet<>();
            for (Floor floor : floors) {
                floorSet.add(floor.getName());
            }
            return floorSet;
        } else return new TreeSet<>();
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

    @PostMapping("save")
    public ResponseEntity<Void> saveFlat(@RequestBody FlatDTO flat) {
        flatService.saveFlat(flat);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/update")
    public ResponseEntity<Void> updateFlat(@PathVariable Long id,
                                           @RequestBody FlatDTO flat) {
        flatService.updateFlat(id, flat);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<Void> deleteFlat(@PathVariable Long id) {
        flatRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
