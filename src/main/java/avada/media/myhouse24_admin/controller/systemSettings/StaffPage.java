package avada.media.myhouse24_admin.controller.systemSettings;

import avada.media.myhouse24_admin.model.Staff;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("system-settings/staff")
public class StaffPage {

    @RequestMapping({"/", ""})
    public ModelAndView showStaffPage() {
        return new ModelAndView("pages/system-settings/staff");
    }

    @GetMapping("get-all")
    public @ResponseBody List<Staff> getAllStaff() {
        return new ArrayList<>();
    }

}
