package avada.media.myhouse24_admin.controller.pages.websiteControl;

import avada.media.myhouse24_admin.model.common.Seo;
import avada.media.myhouse24_admin.model.websiteControl.extra.WebExtraInformation;
import avada.media.myhouse24_admin.model.websiteControl.pages.WebAboutUs;
import avada.media.myhouse24_admin.repo.SeoRepo;
import avada.media.myhouse24_admin.repo.websiteControl.WebAboutUsRepo;
import avada.media.myhouse24_admin.service.WebAboutUsService;
import avada.media.myhouse24_admin.service.WebExtraInformationService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("website-control/web-about-us")
public class WebAboutUsPageController {

    private final WebAboutUsService webAboutUsService;
    private final WebAboutUsRepo webAboutUsRepo;
    private final WebExtraInformationService webExtraInformationService;
    private final SeoRepo seoRepo;

    @GetMapping({"/", ""})
    public ModelAndView showWebAboutUsPage() {
        return new ModelAndView("pages/website-control/web-about-us");
    }

    @GetMapping("get")
    @Transactional
    public @ResponseBody WebAboutUs getWebAboutUs() {
        WebAboutUs webAboutUs = webAboutUsRepo.findById(1L).orElseGet(webAboutUsService::createInitialWebAboutUs);
        Hibernate.initialize(webAboutUs.getGallery());
        Hibernate.initialize(webAboutUs.getDocuments());
        return webAboutUsRepo.findById(1L).orElseGet(webAboutUsService::createInitialWebAboutUs);
    }

    @PostMapping("update")
    public ResponseEntity<Void> updateWebAboutUs(@RequestPart WebAboutUs webAboutUs,
                                                 @RequestPart(required = false) List<MultipartFile> gallery,
                                                 @RequestPart(required = false) MultipartFile photoOfDirector,
                                                 @RequestPart WebExtraInformation webExtraInformation,
                                                 @RequestPart(required = false) List<MultipartFile> extraGallery,
                                                 @RequestPart(required = false) List<MultipartFile> webDocumentsFiles,
                                                 @RequestPart Seo seo) {
        webAboutUsService.updateWebAboutUs(webAboutUs, photoOfDirector, webDocumentsFiles, gallery);
        webExtraInformationService.updateWebExtraInformation(webExtraInformation, extraGallery);
        seoRepo.save(seo);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("web-document/{id}/delete")
    public ResponseEntity<Void> deleteWebDocument(@PathVariable Long id) {
        webAboutUsService.deleteWebDocument(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("gallery/{id}/delete")
    public ResponseEntity<Void> deleteGalleryImage(@PathVariable Long id) {
        webAboutUsService.deleteGalleryImage(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("extra-gallery/{id}/delete")
    public ResponseEntity<Void> deleteExtraGalleryImage(@PathVariable Long id) {
        webAboutUsService.deleteExtraGalleryImage(id);
        return ResponseEntity.ok().build();
    }

}

