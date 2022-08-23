package avada.media.myhouse24_admin.controller.systemSettings;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("system-settings/roles")
public class RolesPage {

    @RequestMapping({"/", ""})
    public ModelAndView showRolesPage() {
        return new ModelAndView("pages/system-settings/roles");
    }

}
