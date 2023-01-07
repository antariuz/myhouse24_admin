package avada.media.myhouse24_admin.controller.pages.websiteControl;

import avada.media.myhouse24_admin.model.websiteControl.pages.WebFeedback;
import avada.media.myhouse24_admin.repo.SeoRepo;
import avada.media.myhouse24_admin.repo.websiteControl.WebFeedbackRepo;
import avada.media.myhouse24_admin.service.WebFeedbackService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Controller
@RequiredArgsConstructor
@RequestMapping("website-control/web-feedback")
public class WebFeedbackPageController {

    private final WebFeedbackService webFeedbackService;
    private final WebFeedbackRepo webFeedbackRepo;
    private final SeoRepo seoRepo;

    @RequestMapping({"/", ""})
    @Transactional
    public ModelAndView showWebFeedbackPage() {
        WebFeedback webFeedback = webFeedbackRepo.findById(1L).orElseGet(webFeedbackService::createInitialWebFeedback);
        Hibernate.initialize(webFeedback.getSeo());
        return new ModelAndView("pages/website-control/web-feedback", "webFeedback", webFeedback);
    }

    @PostMapping("update")
    public String updateWebFeedback(@ModelAttribute WebFeedback webFeedback) {
        seoRepo.save(webFeedback.getSeo());
        webFeedbackRepo.save(webFeedback);
        return "redirect:/website-control/web-feedback";
    }

}
