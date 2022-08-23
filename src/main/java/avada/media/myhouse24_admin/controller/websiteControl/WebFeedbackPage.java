package avada.media.myhouse24_admin.controller.websiteControl;

import avada.media.myhouse24_admin.model.websiteControl.extra.Seo;
import avada.media.myhouse24_admin.model.websiteControl.pages.WebFeedback;
import avada.media.myhouse24_admin.repo.websiteControl.SeoRepo;
import avada.media.myhouse24_admin.repo.websiteControl.WebFeedbackRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("website-control/web-feedback")
public class WebFeedbackPage {

    private final WebFeedbackRepo webFeedbackRepo;
    private final SeoRepo seoRepo;

    @RequestMapping({"/", ""})
    public ModelAndView showWebFeedbackPage() {
        ModelAndView mav = new ModelAndView("pages/website-control/web-feedback");
        WebFeedback webFeedback;
        if (webFeedbackRepo.findById(1L).isPresent()) webFeedback = webFeedbackRepo.findWithSeoWebFeedbackById(1L);
        else {
            webFeedback = new WebFeedback();
            Seo seo = new Seo();
            webFeedback.setSeo(seo);
            seoRepo.save(seo);
            webFeedbackRepo.save(webFeedback);
        }
        mav.addObject("webFeedback", webFeedback);
        return mav;
    }

    @PostMapping("update")
    public String updateWebFeedback(@ModelAttribute WebFeedback webFeedback) {
        seoRepo.save(webFeedback.getSeo());
        webFeedbackRepo.save(webFeedback);
        return "redirect:/website-control/web-feedback";
    }

}
