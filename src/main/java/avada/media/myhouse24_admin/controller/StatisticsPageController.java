package avada.media.myhouse24_admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/statistics")
public class StatisticsPageController {

    @GetMapping({"/", ""})
    public ModelAndView showStatisticsPage() {
        return new ModelAndView("pages/statistics/index");
    }

}
