package avada.media.myhouse24_admin.controller.pages.websiteControl;

import avada.media.myhouse24_admin.model.common.Seo;
import avada.media.myhouse24_admin.model.websiteControl.extra.WebService;
import avada.media.myhouse24_admin.repo.SeoRepo;
import avada.media.myhouse24_admin.repo.websiteControl.WebServiceRepo;
import avada.media.myhouse24_admin.repo.websiteControl.WebServicesRepo;
import avada.media.myhouse24_admin.service.WebServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("website-control/web-services")
public class WebServicesPageController {

    private final WebServicesService webServicesService;
    private final WebServicesRepo webServicesRepo;
    private final WebServiceRepo webServiceRepo;
    private final SeoRepo seoRepo;

    @RequestMapping({"/", ""})
    public ModelAndView showWebServicesPage() {
        if (!webServicesRepo.findById(1L).isPresent()) webServicesService.createInitialWebServices();
        return new ModelAndView("pages/website-control/web-services");
    }

    @GetMapping("get-all")
    public @ResponseBody List<WebService> getAllWebServices() {
        return webServiceRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @PostMapping("save")
    public ResponseEntity<Void> saveWebservice(@RequestPart WebService webService,
                                               @RequestPart(required = false) MultipartFile image) {
        webServicesService.saveWebservice(webService, image);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<Void> deleteWebservice(@PathVariable Long id) {
        webServiceRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("getSeo")
    public @ResponseBody Seo getSeo() {
        return webServicesRepo.findWithSeoWebServicesById(1L).getSeo();
    }

    @PostMapping("updateSeo")
    public ResponseEntity<Void> updateSeo(Seo seo) {
        seoRepo.save(seo);
        return ResponseEntity.ok().build();
    }

}
