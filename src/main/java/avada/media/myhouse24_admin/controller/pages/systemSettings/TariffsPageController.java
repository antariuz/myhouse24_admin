package avada.media.myhouse24_admin.controller.pages.systemSettings;

import avada.media.myhouse24_admin.model.dto.ServiceDTO;
import avada.media.myhouse24_admin.model.dto.TariffDTO;
import avada.media.myhouse24_admin.model.dto.TariffServiceDTO;
import avada.media.myhouse24_admin.model.systemSettings.extra.TariffService;
import avada.media.myhouse24_admin.model.systemSettings.pages.Service;
import avada.media.myhouse24_admin.model.systemSettings.pages.Tariff;
import avada.media.myhouse24_admin.repo.systemSettings.ServiceRepo;
import avada.media.myhouse24_admin.repo.systemSettings.TariffRepo;
import avada.media.myhouse24_admin.repo.systemSettings.TariffServiceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("system-settings/tariffs")
@RequiredArgsConstructor
public class TariffsPageController {

    private final TariffRepo tariffRepo;
    private final TariffServiceRepo tariffServiceRepo;
    private final ServiceRepo serviceRepo;

    @GetMapping({"/", ""})
    public ModelAndView showServicesPage() {
        return new ModelAndView("pages/system-settings/tariffs");
    }

    @GetMapping("get-all")
    @Transactional
    public @ResponseBody List<TariffDTO> getAllTariffs() {
        List<TariffDTO> tariffDTOList = new ArrayList<>();
        for (Tariff tariff : tariffRepo.findAll(Sort.by(Sort.Direction.ASC, "id"))) {
            TariffDTO tariffDTO = new TariffDTO();
            tariffDTO.setId(tariff.getId());
            tariffDTO.setName(tariff.getName());
            tariffDTO.setDescription(tariff.getDescription());
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy - hh:mm");
            tariffDTO.setUpdatedAt(dateFormat.format(tariff.getUpdatedAt()));
            List<TariffServiceDTO> tariffServiceDTOList = new ArrayList<>();
            for (TariffService tariffService : tariff.getTariffServices()) {
                TariffServiceDTO tariffServiceDTO = new TariffServiceDTO();
                tariffServiceDTO.setId(tariffService.getId());
                ServiceDTO serviceDTO = new ServiceDTO();
                serviceDTO.setId(tariffService.getService().getId());
                serviceDTO.setName(tariffService.getService().getName());
                serviceDTO.setUnit(tariffService.getService().getUnit());
                tariffServiceDTO.setService(serviceDTO);
                tariffServiceDTO.setPrice(tariffService.getPrice());
                tariffServiceDTOList.add(tariffServiceDTO);
            }
            tariffDTO.setTariffServices(tariffServiceDTOList);
            tariffDTOList.add(tariffDTO);
        }
        return tariffDTOList;
    }

    @PostMapping("save")
    public ResponseEntity<Void> saveTariff(@RequestBody TariffDTO tariffDTO) {
        Tariff tariff = new Tariff();
        tariff.setId(tariffDTO.getId());
        tariff.setName(tariffDTO.getName());
        tariff.setDescription(tariffDTO.getDescription());
        List<TariffService> tariffServices = new ArrayList<>();
        for (TariffServiceDTO tariffServiceDTO : tariffDTO.getTariffServices()) {
            TariffService tariffService = new TariffService();
            Service service = serviceRepo.findById(tariffServiceDTO.getService().getId()).orElseThrow(() -> new EntityNotFoundException("Service not found with ID: " + tariffServiceDTO.getService().getId()));
            service.setUsed(true);
            serviceRepo.save(service);
            tariffService.setService(service);
            tariffService.setPrice(tariffServiceDTO.getPrice());
            tariffServiceRepo.save(tariffService);
            tariffServices.add(tariffService);
        }
        tariff.setTariffServices(tariffServices);
        tariffRepo.save(tariff);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<Void> deleteTariff(@PathVariable Long id) {
        tariffRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
