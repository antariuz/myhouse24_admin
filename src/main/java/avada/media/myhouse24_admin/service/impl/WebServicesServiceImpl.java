package avada.media.myhouse24_admin.service.impl;

import avada.media.myhouse24_admin.model.common.FileUtil;
import avada.media.myhouse24_admin.model.common.Seo;
import avada.media.myhouse24_admin.model.websiteControl.extra.WebService;
import avada.media.myhouse24_admin.model.websiteControl.pages.WebServices;
import avada.media.myhouse24_admin.repo.SeoRepo;
import avada.media.myhouse24_admin.repo.websiteControl.WebServiceRepo;
import avada.media.myhouse24_admin.repo.websiteControl.WebServicesRepo;
import avada.media.myhouse24_admin.service.WebServicesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebServicesServiceImpl implements WebServicesService {

    private final WebServicesRepo webServicesRepo;
    private final WebServiceRepo webServiceRepo;
    private final SeoRepo seoRepo;

    @Override
    @Transactional
    public void createInitialWebServices() {
        WebServices webServices = new WebServices();
        Seo seo = new Seo();
        seo.setTitle("SEO title of WebServices");
        seo.setDescription("SEO description of WebServices");
        seo.setKeywords("SEO keywords of WebServices");
        webServices.setSeo(seo);
        seoRepo.save(seo);
        List<WebService> webServiceList = webServices.getWebServiceList();
        for (int i = 0; i < 3; i++) {
            WebService webService = new WebService();
            webService.setTitle("Lorem Ipsum");
            webService.setDescription("Maecenas id rutrum est. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Cras in arcu quis dolor imperdiet imperdiet. Nunc laoreet mi ut orci faucibus pulvinar. Vestibulum in condimentum dui. Praesent commodo accumsan nisi vitae interdum. Sed lorem ante, accumsan quis aliquam eget, dignissim sed risus. Nulla facilisi. Fusce non euismod nisi. Nullam eget dictum lorem. Quisque quis tempor elit. In tortor tortor, sagittis vitae enim a, pellentesque commodo nisi.");
            webServiceList.add(webService);
            webServicesRepo.save(webServices);
        }
        webServices.setWebServiceList(webServiceList);
        webServicesRepo.save(webServices);
    }

    @Override
    public void saveWebservice(WebService webService, MultipartFile image) {
        WebServices webServices = webServicesRepo.findWithServiceListWebServicesById(1L);
        try {
            if (image != null) {
                String imageName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
                String uploadDir = "/webServices";
                if (!imageName.equals("")) {
                    FileUtil.saveFile(uploadDir, imageName, image);
                    webService.setImage(imageName);
                } else throw new FileNotFoundException("Image file has not been uploaded");
            } else if (webService.getId() != null) {
                String prevImage = webServiceRepo.findById(webService.getId()).orElse(new WebService()).getImage();
                webService.setImage(prevImage);
            }
        } catch (IOException e) {
            log.error("Image file has not been uploaded. Message: " + e.getMessage());
        }
        if (webService.getId() == null) {
            webServiceRepo.save(webService);
            List<WebService> webServiceList = webServices.getWebServiceList();
            webServiceList.add(webService);
            webServices.setWebServiceList(webServiceList);
            webServicesRepo.save(webServices);
        } else webServiceRepo.save(webService);
    }
}
