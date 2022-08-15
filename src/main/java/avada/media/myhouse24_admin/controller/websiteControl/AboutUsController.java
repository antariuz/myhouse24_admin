package avada.media.myhouse24_admin.controller.websiteControl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AboutUsController {

    @GetMapping("about-us-admin")
    public String getMainPage(Model model){
       // model.addAttribute("aboutUsAttr", aboutUsRepository.findById(1L));
       // model.addAttribute("photoAboutUsAttr", photoAboutUsRepository.findAll());
       // model.addAttribute("myDocumentsAttr", myDocumentRepository.findAll());
      //  model.addAttribute("additionalPhotoAboutUsAttr", additionalPhotoAboutUsRepository.findAll());
        return "pages/website-control/about-us-admin";
    }
}
