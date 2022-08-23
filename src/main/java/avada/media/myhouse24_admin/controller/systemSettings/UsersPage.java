package avada.media.myhouse24_admin.controller.systemSettings;

import avada.media.myhouse24_admin.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("system-settings/users")
public class UsersPage {

    @RequestMapping({"/", ""})
    public ModelAndView showUsersPage() {
        return new ModelAndView("pages/system-settings/users");
    }

    @GetMapping("get-all")
    public @ResponseBody List<User> getAllUsers() {
        return new ArrayList<>();
    }

}
