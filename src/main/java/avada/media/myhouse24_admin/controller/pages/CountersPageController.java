package avada.media.myhouse24_admin.controller.pages;

import avada.media.myhouse24_admin.model.dto.BuildingDTO;
import avada.media.myhouse24_admin.model.dto.CounterDTO;
import avada.media.myhouse24_admin.model.dto.ServiceDTO;
import avada.media.myhouse24_admin.model.dto.StatusDTO;
import avada.media.myhouse24_admin.model.request.CounterRequest;
import avada.media.myhouse24_admin.model.response.ResponseByPage;
import avada.media.myhouse24_admin.service.BuildingService;
import avada.media.myhouse24_admin.service.CounterService;
import avada.media.myhouse24_admin.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("counters")
public class CountersPageController {

    private final CounterService counterService;
    private final BuildingService buildingService;
    private final ServiceService serviceService;

    @GetMapping({"/", ""})
    public ModelAndView showCountersPage() {
        return new ModelAndView("pages/counters");
    }

    @GetMapping("get-all-counters")
    public @ResponseBody ResponseByPage<CounterDTO> getAllCounters(CounterRequest counterRequest) {
        return counterService.getAllCounters(counterRequest);
    }

    @GetMapping("get-new-unique-number")
    public @ResponseBody String getNewUniqueNumber() {
        return counterService.getNewUniqueNumber();
    }

    @PostMapping("is-unique-number-not-exists")
    public @ResponseBody boolean isUniqueNumberNotExists(@RequestParam(required = false) Long id, String uniqueNumber) {
        return counterService.isUniqueNumberNotExists(id, uniqueNumber);
    }

    @GetMapping("get-all-status")
    public @ResponseBody List<StatusDTO> getAllStatus() {
        return counterService.getAllStatus();
    }

    @PostMapping("save")
    public ResponseEntity<Void> saveCounter(@RequestBody CounterDTO counterDTO) {
        counterService.saveCounter(counterDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/update")
    public ResponseEntity<Void> updateCounter(@PathVariable Long id,
                                              @RequestBody CounterDTO counterDTO) {
        counterService.updateCounter(id, counterDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<Void> deleteCounter(@PathVariable Long id) {
        counterService.deleteCounter(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("get-all-buildings")
    public @ResponseBody List<BuildingDTO> getAllBuildings() {
        return buildingService.getAllBuildingsDTO();
    }

    @GetMapping("get-all-services")
    public @ResponseBody List<ServiceDTO> getAllServices() {
        return serviceService.getAllServicesDTO();
    }

}
