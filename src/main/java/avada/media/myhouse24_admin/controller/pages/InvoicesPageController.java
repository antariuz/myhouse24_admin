package avada.media.myhouse24_admin.controller.pages;

import avada.media.myhouse24_admin.model.dto.*;
import avada.media.myhouse24_admin.model.request.InvoiceRequest;
import avada.media.myhouse24_admin.model.systemSettings.extra.Unit;
import avada.media.myhouse24_admin.repo.systemSettings.TariffRepo;
import avada.media.myhouse24_admin.repo.systemSettings.UnitRepo;
import avada.media.myhouse24_admin.service.BuildingService;
import avada.media.myhouse24_admin.service.InvoiceService;
import avada.media.myhouse24_admin.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("invoices")
public class InvoicesPageController {

    private final InvoiceService invoiceService;
    private final BuildingService buildingService;
    private final ServiceService serviceService;
    private final TariffRepo tariffRepo;
    private final UnitRepo unitRepo;

    @GetMapping({"/", ""})
    public ModelAndView showInvoicesPage() {
        return new ModelAndView("pages/invoices");
    }

    @GetMapping("get-all-invoices")
    public @ResponseBody ResponseByPage<InvoiceDTO> getAllInvoices(InvoiceRequest request) {
        return invoiceService.getAllInvoices(request);
    }

    @GetMapping("get-new-unique-number")
    public @ResponseBody String getNewUniqueNumber() {
        return invoiceService.getNewUniqueNumber();
    }

    @PostMapping("is-unique-number-not-exists")
    public @ResponseBody boolean isUniqueNumberNotExists(@RequestParam(required = false) Long id, String uniqueNumber) {
        return invoiceService.isUniqueNumberNotExists(id, uniqueNumber);
    }

    @GetMapping("get-all-status")
    public @ResponseBody List<StatusDTO> getAllStatus() {
        return invoiceService.getAllStatus();
    }

    @PostMapping("save")
    public ResponseEntity<Void> saveInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        invoiceService.saveInvoice(invoiceDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/update")
    public ResponseEntity<Void> updateInvoice(@PathVariable Long id,
                                              @RequestBody InvoiceDTO invoiceDTO) {
        invoiceService.updateInvoice(id, invoiceDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
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

    @GetMapping("get-all-tariffs")
    public @ResponseBody List<TariffDTO> getAllTariffs() {
        return tariffRepo
                .findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(tariff -> new TariffDTO(tariff.getId(), tariff.getName()))
                .collect(Collectors.toList());
    }

    @GetMapping("get-all-units")
    public @ResponseBody List<Unit> getAllUnits() {
        return unitRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

}
