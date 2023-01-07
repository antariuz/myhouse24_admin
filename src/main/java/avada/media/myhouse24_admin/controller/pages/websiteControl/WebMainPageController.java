package avada.media.myhouse24_admin.controller.pages.websiteControl;

import avada.media.myhouse24_admin.model.common.Seo;
import avada.media.myhouse24_admin.model.dto.WebMainDTO;
import avada.media.myhouse24_admin.model.websiteControl.extra.WebNextToUs;
import avada.media.myhouse24_admin.model.websiteControl.pages.WebMain;
import avada.media.myhouse24_admin.repo.SeoRepo;
import avada.media.myhouse24_admin.repo.websiteControl.WebMainRepo;
import avada.media.myhouse24_admin.service.WebMainService;
import avada.media.myhouse24_admin.service.WebNextToUsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("website-control/web-main")
public class WebMainPageController {

    private final WebMainRepo webMainRepo;
    private final WebMainService webMainService;
    private final WebNextToUsService webNextToUsService;
    private final SeoRepo seoRepo;

    @GetMapping({"/", ""})
    public ModelAndView showWebMainPage() {
        return new ModelAndView("pages/website-control/web-main");
    }

    @PutMapping("{id}/update")
    public ResponseEntity<Void> updateWebMain(@PathVariable Long id,
                                              @RequestPart WebMainDTO webMainDTO,
                                              @RequestPart List<MultipartFile> slides,
                                              @RequestPart List<WebNextToUs> webNextToUsList,
                                              @RequestPart List<MultipartFile> nextToUsImages,
                                              @RequestPart Seo seo) {
        webMainService.updateWebMain(id, webMainDTO, slides);
        webNextToUsService.updateWebNextToUs(webNextToUsList, nextToUsImages);
        seoRepo.save(seo);
        return ResponseEntity.ok().build();
    }

    @GetMapping("get")
    public @ResponseBody WebMain getWebMain() {
        return webMainRepo.findById(1L).orElseGet(webMainService::createInitialWebMain);
    }

}
