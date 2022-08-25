package avada.media.myhouse24_admin.controller.systemSettings;

import avada.media.myhouse24_admin.dto.ServiceDto;
import avada.media.myhouse24_admin.model.systemSettings.Service;
import avada.media.myhouse24_admin.model.systemSettings.Unit;
import avada.media.myhouse24_admin.repo.systemSettings.ServiceRepo;
import avada.media.myhouse24_admin.repo.systemSettings.UnitRepo;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class AdminServiceController {

    private final UnitRepo unitRepo;
    private final ServiceRepo serviceRepo;
    private final static Gson gson = new Gson();

    @GetMapping("/system-settings/admin-service")
    public ModelAndView showAdminServicePage() {
        ModelAndView modelAndView = new ModelAndView("pages/system-settings/admin-service");

        modelAndView.addObject("unitAttr", unitRepo.findAll());
        modelAndView.addObject("serviceAttr", serviceRepo.findAll());
        return modelAndView;
    }


    @RequestMapping(value="/add-units", method= RequestMethod.POST, produces = "application/json", consumes="application/json")
    @ResponseBody
    public String postTestRequest(@RequestBody String jsonUnits) {
       List<String> unitList =  gson.fromJson(jsonUnits, List.class);
        for (String someUnit : unitList) {
            Unit unit = new Unit();
            unit.setName(someUnit);
            unitRepo.save(unit);
       }
        return "redirect:/system-settings/admin-service";
    }

  //  @PostMapping("/add-units")
  //  public String postMailUserChoice() {
  //      return "redirect:/system-tuning/admin-service";
   // }


    // обновление данных поля 'name' конкретного, уже существующего объекта Unit
    @RequestMapping(value="/update-units", method= RequestMethod.POST, produces = "application/json", consumes="application/json")
    public void updateUnits(@RequestBody String sendJsonData ) {
        List<String> sendData =  gson.fromJson(sendJsonData, List.class);
        Long currentId = Long.valueOf(sendData.get(0));
        Unit unit = unitRepo.findById(currentId).get();
        unit.setName(sendData.get(1));
        unitRepo.save(unit);
    }

    @PostMapping("/update-units")
    public String postUpdateUnits() {
        return "redirect:/system-settings/admin-service";
   }

    // удаление уже существующего объекта Unit
    @PostMapping("/unit/remove/{id}")
    public void removeUnitFromDB(@PathVariable Long id) {
        unitRepo.deleteById(id);
      //  return "redirect:/system-settings/admin-service";
    }

    //хотел для вывода в ajax
    // пока не используется
    @GetMapping("/system-settings/get-all-units")
    public @ResponseBody List<Unit> getAllWebUnits() {
        return unitRepo.findAll();
    }

    // удаление уже существующего объекта Service
    @GetMapping("/service/remove/{id}")
    public void removeServiceFromDB(@PathVariable Long id) {
        Service service = serviceRepo.findById(id).get();
        Unit unit = service.getUnit();
        List<Service> services = serviceRepo.findAll();
        int count = 0;
        for (Service currentService : services) {
            if (currentService.getUnit().getName() == unit.getName()) {
                count++;
            }
        }
        if (count <= 1) {
            unit.setUsed(false);
            unitRepo.save(unit);
        }
        serviceRepo.deleteById(id);
    }


    // обновление данных поля 'name' конкретного, уже существующего объекта Service
    @RequestMapping(value="/update-service-name", method= RequestMethod.POST, produces = "application/json", consumes="application/json")
    public void updateServiceName(@RequestBody String sendJsonData ) {
        List<String> sendData =  gson.fromJson(sendJsonData, List.class);
        Long currentId = Long.valueOf(sendData.get(0));
        Service service = serviceRepo.findById(currentId).get();
        service.setName(sendData.get(1));
        serviceRepo.save(service);
    }

    // обновление данных поля 'select' конкретного, уже существующего объекта Service
    @RequestMapping(value="/update-service-select", method= RequestMethod.POST, produces = "application/json", consumes="application/json")
    public void updateServiceSelectData(@RequestBody String sendJsonData ) {
        List<String> sendData =  gson.fromJson(sendJsonData, List.class);
        Long serviceId = Long.valueOf(sendData.get(1));
        System.out.println();
        Long unitId = Long.valueOf(sendData.get(0));
        Service service = serviceRepo.findById(serviceId).get();
        service.setUnit(unitRepo.findById(unitId).get());
        serviceRepo.save(service);
    }

    // обновление данных поля 'showInCounters' конкретного, уже существующего объекта Service
    @RequestMapping(value="/update-service-show-in-counters", method= RequestMethod.POST, produces = "application/json", consumes="application/json")
    public void updateServiceShowInCounters(@RequestBody Long sendJsonData ) {
        Service service = serviceRepo.findById(sendJsonData).get();
        if (service.isShowInCounters()){
            service.setShowInCounters(false);
        } else {
            service.setShowInCounters(true);
        }
        serviceRepo.save(service);
    }

    @RequestMapping(value="/add-services", method= RequestMethod.POST, produces = "application/json", consumes="application/json")
    @ResponseBody
    public void addNewServices(@RequestBody ArrayList<ServiceDto> jsonServices) {
       for (ServiceDto current : jsonServices) {
           Service service = new Service();
           service.setName(current.getName());
           service.setShowInCounters(current.isShowInCounters());
               Unit unit = unitRepo.findById(current.getUnitId()).get();
               service.setUnit(unit);
               serviceRepo.save(service);
               if (!unit.isUsed()) {
                   unit.setUsed(true);
                   unitRepo.save(unit);
               }

        }
    }

    @PostMapping("/add-services")
    public String postNewServices() {
        return "redirect:/system-settings/admin-service";
    }
}
