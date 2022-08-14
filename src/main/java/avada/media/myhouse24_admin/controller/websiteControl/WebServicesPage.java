package avada.media.myhouse24_admin.controller.websiteControl;

import avada.media.myhouse24_admin.model.website.pages.WebServices;
import avada.media.myhouse24_admin.repo.website.WebServicesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("website-control/web-services")
public class WebServicesPage {

    private final WebServicesRepo webServicesRepo;

    @GetMapping({"/", ""})
    public ModelAndView showWebServicesPage() {
        ModelAndView mav = new ModelAndView("pages/website-control/web-services");
        WebServices webServices;
        if (webServicesRepo.findById(1L).isPresent()) webServices = webServicesRepo.findById(1L).get();
        else webServices = webServicesRepo.save(new WebServices());
        mav.addObject("webservices", webServices);
        return mav;
    }

}
