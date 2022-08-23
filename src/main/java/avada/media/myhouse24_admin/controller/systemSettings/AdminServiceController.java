package avada.media.myhouse24_admin.controller.systemSettings;

import avada.media.myhouse24_admin.model.systemSettings.Unit;
import avada.media.myhouse24_admin.repo.systemSettings.ServiceRepo;
import avada.media.myhouse24_admin.repo.systemSettings.UnitRepo;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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


    @RequestMapping(value = "/add-units", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public void postTestRequest(@RequestBody String jsonUnits) {
        //     if (jsonUnits.length() == 1){
        //         String unitList =  gson.fromJson(jsonUnits, String.class);
        //         Unit unit = new Unit();
        //         unit.setName(unitList);
        //         unitRepo.save(unit);
        //     } else {
        List<String> unitList = gson.fromJson(jsonUnits, List.class);
        for (String someUnit : unitList) {
            Unit unit = new Unit();
            unit.setName(someUnit);
            unitRepo.save(unit);
            //    }
        }
    }


    @PostMapping("/add-units")
    public String postMailUserChoice() {
        return "redirect:/system-settings/admin-service";
    }


    // обновление данных поля 'name' конкретного, уже существующего объекта Unit
    @RequestMapping(value = "/update-units", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public void updateUnits(@RequestBody String sendJsonData) {
        List<String> sendData = gson.fromJson(sendJsonData, List.class);
        Long currentId = Long.valueOf(sendData.get(0));
        Unit unit = unitRepo.findById(currentId).get();
        unit.setName(sendData.get(1));
        unitRepo.save(unit);
    }

    // удаление уже существующего объекта Unit
    @GetMapping("/unit/remove/{id}")
    public void removePhotoAboutUs(@PathVariable Long id) {
        unitRepo.deleteById(id);
        //  return "redirect:/system-settings/admin-service";
    }
}
