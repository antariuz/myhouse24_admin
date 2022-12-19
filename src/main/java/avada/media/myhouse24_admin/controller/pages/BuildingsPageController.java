package avada.media.myhouse24_admin.controller.pages;

import avada.media.myhouse24_admin.model.Building;
import avada.media.myhouse24_admin.model.dto.BuildingDTO;
import avada.media.myhouse24_admin.model.dto.ResponseByPage;
import avada.media.myhouse24_admin.model.dto.StaffDTO;
import avada.media.myhouse24_admin.model.request.BuildingRequest;
import avada.media.myhouse24_admin.service.BuildingService;
import avada.media.myhouse24_admin.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("buildings")
@RequiredArgsConstructor
public class BuildingsPageController {

    private final BuildingService buildingService;
    private final StaffService staffService;

    @GetMapping({"/", ""})
    public ModelAndView showBuildingsPage() {
        return new ModelAndView("pages/buildings");
    }

    @GetMapping("get-all-buildings")
    public @ResponseBody ResponseByPage<BuildingDTO> getAllBuildings(BuildingRequest buildingRequest) {
        return buildingService.getAllBuildingsDTO(buildingRequest);
    }

    @GetMapping("get-all-staff")
    public @ResponseBody List<StaffDTO> getAllStaffExceptInactive() {
        return staffService.getAllStaffDTOExceptInactive();
    }

    @PostMapping("save")
    public ResponseEntity<Void> saveBuilding(@RequestPart Building building,
                                             @RequestPart(required = false) List<MultipartFile> images) {
        buildingService.saveBuilding(building, images);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/update")
    public ResponseEntity<Void> updateBuilding(@PathVariable Long id,
                                               @RequestPart Building building,
                                               @RequestPart(required = false) List<MultipartFile> images) {
        buildingService.updateBuilding(id, building, images);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<Void> saveBuilding(@PathVariable Long id) {
        buildingService.deleteBuildingById(id);
        return ResponseEntity.ok().build();
    }

}
