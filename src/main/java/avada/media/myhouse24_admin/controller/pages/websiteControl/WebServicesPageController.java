package avada.media.myhouse24_admin.controller.pages.websiteControl;

import avada.media.myhouse24_admin.model.common.FileUtil;
import avada.media.myhouse24_admin.model.common.Seo;
import avada.media.myhouse24_admin.model.websiteControl.extra.WebService;
import avada.media.myhouse24_admin.model.websiteControl.pages.WebServices;
import avada.media.myhouse24_admin.repo.SeoRepo;
import avada.media.myhouse24_admin.repo.websiteControl.WebServiceRepo;
import avada.media.myhouse24_admin.repo.websiteControl.WebServicesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("website-control/web-services")
public class WebServicesPageController {

    private final WebServicesRepo webServicesRepo;
    private final WebServiceRepo webServiceRepo;
    private final SeoRepo seoRepo;

    @RequestMapping({"/", ""})
    public ModelAndView showWebServicesPage() {
        if (!webServicesRepo.findById(1L).isPresent()) {
            WebServices webServices = new WebServices();
            Seo seo = new Seo();
            webServices.setSeo(seo);
            seoRepo.save(seo);
            webServicesRepo.save(webServices);
        }
        return new ModelAndView("pages/website-control/web-services");
    }

    @GetMapping("get-all")
    public @ResponseBody List<WebService> getAllWebServices() {
        return webServiceRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @PostMapping("save")
    public ResponseEntity<Void> addWebservice(@RequestPart WebService webService,
                                              @RequestPart(required = false) MultipartFile image) throws IOException {
        WebServices webServices = webServicesRepo.findWithServiceListWebServicesById(1L);
        if (image != null) {
            String imageName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            String uploadDir = "/webServices";
            if (!imageName.equals("")) {
                FileUtil.saveFile(uploadDir, imageName, image);
                webService.setImage(imageName);
            } else throw new FileNotFoundException("Image file has not been uploaded");
        } else if (webService.getId() != null) {
            String prevImage = webServiceRepo.findById(webService.getId()).get().getImage();
            webService.setImage(prevImage);
        }
        if (webService.getId() == null) {
            webServiceRepo.save(webService);
            List<WebService> webServiceList = webServices.getWebServiceList();
            webServiceList.add(webService);
            webServices.setWebServiceList(webServiceList);
            webServicesRepo.save(webServices);
        } else webServiceRepo.save(webService);
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
