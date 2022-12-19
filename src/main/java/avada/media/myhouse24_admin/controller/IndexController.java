package avada.media.myhouse24_admin.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    //  TODO: rework accesses and redirects to web pages
    @RequestMapping({"/", ""})
    public ModelAndView showDefaultPage(Authentication authentication) {
        ModelAndView mav = new ModelAndView();
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_DIRECTOR")) ||
                authentication.getAuthorities().contains(new SimpleGrantedAuthority("STATISTICS"))) {
            mav.setViewName("redirect:/statistics");
        } else {
            mav.setViewName("redirect:/system-settings/transaction-purposes");
        }
        return mav;
    }

}
