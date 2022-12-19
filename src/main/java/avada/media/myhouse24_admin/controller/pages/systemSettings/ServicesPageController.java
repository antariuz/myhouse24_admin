package avada.media.myhouse24_admin.controller.pages.systemSettings;

import avada.media.myhouse24_admin.model.dto.ServiceDTO;
import avada.media.myhouse24_admin.model.systemSettings.extra.Unit;
import avada.media.myhouse24_admin.model.systemSettings.pages.Service;
import avada.media.myhouse24_admin.repo.systemSettings.ServiceRepo;
import avada.media.myhouse24_admin.repo.systemSettings.UnitRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("system-settings/services")
@RequiredArgsConstructor
public class ServicesPageController {

    private final ServiceRepo serviceRepo;
    private final UnitRepo unitRepo;

    @GetMapping({"/", ""})
    public ModelAndView showServicesPage() {
        return new ModelAndView("pages/system-settings/services");
    }

    @GetMapping("get-all-services")
    public @ResponseBody List<Service> getAllServices() {
        return serviceRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @PostMapping("save-services")
    public ResponseEntity<Void> saveServices(@RequestBody List<ServiceDTO> serviceDTOList) {
        for (ServiceDTO serviceDTO : serviceDTOList) {
            if (serviceDTO.getId() != null) {
                Service service = serviceRepo.findById(serviceDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Service not found with ID: " + serviceDTO.getId()));
                service.setName(serviceDTO.getName());
                service.setShowInCounters(serviceDTO.isShowInCounters());
                Unit oldUnitOfService = service.getUnit();
                Unit newUnitOfService = unitRepo.findById(serviceDTO.getUnit().getId()).orElseThrow(() -> new EntityNotFoundException("Unit not found with ID: " + serviceDTO.getUnit().getId()));
                oldUnitOfService.setUsed(false);
                newUnitOfService.setUsed(true);
                unitRepo.saveAll(Arrays.asList(oldUnitOfService, newUnitOfService));
                service.setUnit(newUnitOfService);
                serviceRepo.save(service);
            } else {
                Service service = new Service();
                service.setName(serviceDTO.getName());
                service.setShowInCounters(serviceDTO.isShowInCounters());
                Unit unit = unitRepo.findById(serviceDTO.getUnit().getId()).orElseThrow(() -> new EntityNotFoundException("Unit not found with ID: " + serviceDTO.getUnit().getId()));
                unit.setUsed(true);
                service.setUnit(unit);
                unitRepo.save(unit);
                serviceRepo.save(service);
            }
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        Unit unit = serviceRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Service not found with ID: " + id)).getUnit();
        if (serviceRepo.countByUnitId(unit.getId()) <= 1) {
            unit.setUsed(false);
            unitRepo.save(unit);
        }
        serviceRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("get-all-units")
    public @ResponseBody List<Unit> getAllUnits() {
        return unitRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @PostMapping("save-units")
    public ResponseEntity<Void> saveUnits(@RequestBody List<Unit> units) {
        for (Unit unit : units) {
            if (unit.getId() != null) {
                Unit existedUnit = unitRepo.findById(unit.getId()).orElseThrow(() -> new EntityNotFoundException("Unit not found with ID: " + unit.getId()));
                unit.setName(unit.getName());
                unitRepo.save(existedUnit);
            } else unitRepo.save(unit);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("units/{id}/delete")
    public ResponseEntity<Void> deleteUnit(@PathVariable Long id) {
        unitRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
