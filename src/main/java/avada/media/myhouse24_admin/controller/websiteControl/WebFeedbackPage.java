package avada.media.myhouse24_admin.controller.websiteControl;

import avada.media.myhouse24_admin.model.website.pages.WebFeedback;
import avada.media.myhouse24_admin.repo.website.WebFeedbackRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("website-control/web-feedback")
@Slf4j
public class WebFeedbackPage {

    private final WebFeedbackRepo webFeedbackRepo;

    @GetMapping({"/", ""})
    public ModelAndView showWebFeedbackPage() {
        ModelAndView mav = new ModelAndView("pages/website-control/web-feedback");
        WebFeedback webFeedback;
        if (webFeedbackRepo.findById(1L).isPresent()) webFeedback = webFeedbackRepo.findById(1L).get();
        else webFeedback = webFeedbackRepo.save(new WebFeedback());
        mav.addObject("webFeedback", webFeedback);
        return mav;
    }

    @PostMapping("update")
    public String updateWebFeedback(@ModelAttribute("webFeedback") WebFeedback webFeedback) {
        webFeedbackRepo.save(webFeedback);
        log.info("WebFeedback successfully update");
        return "redirect:/website-control/web-feedback";
    }

}
