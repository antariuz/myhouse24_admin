package avada.media.myhouse24_admin.service.impl;

import avada.media.myhouse24_admin.model.common.FileUtil;
import avada.media.myhouse24_admin.model.common.Seo;
import avada.media.myhouse24_admin.model.websiteControl.extra.Image;
import avada.media.myhouse24_admin.model.websiteControl.extra.WebDocument;
import avada.media.myhouse24_admin.model.websiteControl.extra.WebExtraInformation;
import avada.media.myhouse24_admin.model.websiteControl.pages.WebAboutUs;
import avada.media.myhouse24_admin.repo.ImageRepo;
import avada.media.myhouse24_admin.repo.SeoRepo;
import avada.media.myhouse24_admin.repo.websiteControl.WebAboutUsRepo;
import avada.media.myhouse24_admin.repo.websiteControl.WebDocumentRepo;
import avada.media.myhouse24_admin.repo.websiteControl.WebExtraInformationRepo;
import avada.media.myhouse24_admin.service.WebAboutUsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebAboutUsServiceImpl implements WebAboutUsService {

    private final WebAboutUsRepo webAboutUsRepo;
    private final WebDocumentRepo webDocumentRepo;
    private final ImageRepo imageRepo;
    private final SeoRepo seoRepo;
    private final WebExtraInformationRepo webExtraInformationRepo;

    @Override
    @Transactional
    public WebAboutUs createInitialWebAboutUs() {
        WebAboutUs webAboutUs = new WebAboutUs();
        webAboutUs.setTitle("Lorem Ipsum");
        webAboutUs.setDescription("<p style=\"margin-right: 0px; margin-bottom: 15px; margin-left: 0px; padding: 0px; text-align: justify; color: rgb(0, 0, 0); font-family: &quot;Open Sans&quot;, Arial, sans-serif; font-size: 14px;\">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum at ex lacus. Donec volutpat tortor id risus fermentum varius. Aenean tempus pretium sapien, et fermentum arcu malesuada eu. Duis mattis, erat vitae pharetra egestas, orci est auctor eros, aliquet egestas lorem mauris in ipsum. Nullam porta augue ac facilisis pharetra. Phasellus eget urna posuere, feugiat nulla et, tempor mi. Maecenas porta urna et libero aliquet, vel maximus erat placerat. Praesent sollicitudin lacinia nunc. Aenean lorem augue, blandit id magna sit amet, rutrum commodo magna. Fusce imperdiet, ex eget laoreet egestas, lorem orci efficitur ipsum, eu rutrum magna est at arcu. Mauris ornare malesuada arcu ac dictum. Nullam pretium vestibulum justo, ac egestas dolor scelerisque id. Ut a nunc in dolor dictum lobortis ut in leo. Aenean ut tortor neque. Sed enim ante, varius ut felis vitae, laoreet posuere nunc.</p><p style=\"margin-right: 0px; margin-bottom: 15px; margin-left: 0px; padding: 0px; text-align: justify; color: rgb(0, 0, 0); font-family: &quot;Open Sans&quot;, Arial, sans-serif; font-size: 14px;\">Suspendisse ut lobortis eros. Duis in cursus diam. Fusce vel massa nibh. Nunc in molestie urna, et suscipit quam. Vivamus venenatis turpis sit amet risus interdum, sit amet pharetra magna pulvinar. Cras sodales, risus eu porttitor dignissim, orci nisl auctor nisl, ac tincidunt sem sapien sit amet risus. Vestibulum ac volutpat nibh, id volutpat ex. Nam vel pulvinar tortor, sed vehicula orci.</p><p style=\"margin-right: 0px; margin-bottom: 15px; margin-left: 0px; padding: 0px; text-align: justify; color: rgb(0, 0, 0); font-family: &quot;Open Sans&quot;, Arial, sans-serif; font-size: 14px;\">Maecenas id rutrum est. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Cras in arcu quis dolor imperdiet imperdiet. Nunc laoreet mi ut orci faucibus pulvinar. Vestibulum in condimentum dui. Praesent commodo accumsan nisi vitae interdum. Sed lorem ante, accumsan quis aliquam eget, dignissim sed risus. Nulla facilisi. Fusce non euismod nisi. Nullam eget dictum lorem. Quisque quis tempor elit. In tortor tortor, sagittis vitae enim a, pellentesque commodo nisi.</p>");
        Seo seo = new Seo();
        seo.setTitle("SEO title of WebAboutUs");
        seo.setDescription("SEO description of WebAboutUs");
        seo.setKeywords("SEO keywords of WebAboutUs");
        webAboutUs.setSeo(seo);
        seoRepo.save(seo);
        WebExtraInformation webExtraInformation = new WebExtraInformation();
        webExtraInformation.setTitle("Lorem Ipsum");
        webExtraInformation.setDescription("<p><span style=\"color: rgb(0, 0, 0); font-family: &quot;Open Sans&quot;, Arial, sans-serif; font-size: 14px; text-align: justify;\">Maecenas id rutrum est. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Cras in arcu quis dolor imperdiet imperdiet. Nunc laoreet mi ut orci faucibus pulvinar. Vestibulum in condimentum dui. Praesent commodo accumsan nisi vitae interdum. Sed lorem ante, accumsan quis aliquam eget, dignissim sed risus. Nulla facilisi. Fusce non euismod nisi. Nullam eget dictum lorem. Quisque quis tempor elit. In tortor tortor, sagittis vitae enim a, pellentesque commodo nisi.</span><br></p>");
        webAboutUs.setWebExtraInformation(webExtraInformation);
        webExtraInformationRepo.save(webExtraInformation);
        webAboutUsRepo.save(webAboutUs);
        return webAboutUs;
    }

    @Override
    @Transactional
    public void updateWebAboutUs(WebAboutUs webAboutUsRequested, MultipartFile photoOfDirector, List<MultipartFile> webDocumentsFiles, List<MultipartFile> galleryRequested) {
        WebAboutUs webAboutUs = webAboutUsRepo.findById(webAboutUsRequested.getId()).orElseThrow(() -> new EntityNotFoundException("WebAboutUs not found with ID:" + webAboutUsRequested.getId()));
        webAboutUs.setTitle(webAboutUsRequested.getTitle());
        webAboutUs.setDescription(webAboutUsRequested.getDescription());

        if (photoOfDirector != null) {
            try {
                String changedFileName = "photoOfDirector" + Objects.requireNonNull(photoOfDirector.getOriginalFilename()).substring(photoOfDirector.getOriginalFilename().lastIndexOf("."));
                FileUtil.saveFile("webAboutUs", changedFileName, photoOfDirector);
                webAboutUs.setPhotoOfDirector(changedFileName);
            } catch (IOException e) {
                log.error("Can't save image. Error: " + e.getMessage());
            }
        }

        List<Image> gallery = webAboutUs.getGallery();
        if (galleryRequested != null) {
            for (MultipartFile galleryImage : galleryRequested) {
                try {
                    String changedName = "gallery-image-" + (gallery.size() + 1) + Objects.requireNonNull(galleryImage.getOriginalFilename()).substring(galleryImage.getOriginalFilename().lastIndexOf("."));
                    FileUtil.saveFile("webAboutUs/gallery", changedName, galleryImage);
                    Image image = new Image();
                    image.setName(changedName);
                    gallery.add(image);
                    imageRepo.save(image);
                } catch (IOException e) {
                    log.error("Can't save file. Error: " + e.getMessage());
                }
            }
        }
        webAboutUs.setGallery(gallery);

        List<WebDocument> webDocuments = webAboutUsRequested.getDocuments();
        List<WebDocument> webDocumentList = new ArrayList<>();
        for (int i = 0; i < webDocuments.size(); i++) {
            WebDocument webDocument = webDocuments.get(i);
            if (!Objects.equals(webDocumentsFiles.get(i).getOriginalFilename(), "empty")) {
                try {
                    String changedName = "document-" + (i + 1) + Objects.requireNonNull(webDocumentsFiles.get(i).getOriginalFilename()).substring(Objects.requireNonNull(webDocumentsFiles.get(i).getOriginalFilename()).lastIndexOf("."));
                    FileUtil.saveFile("webAboutUs/documents", changedName, webDocumentsFiles.get(i));
                    webDocument.setName(changedName);
                } catch (IOException e) {
                    log.error("Can't save file. Error: " + e.getMessage());
                }
            } else if (webDocument.getId() != null) {
                webDocument.setName(webDocumentRepo.findById(webDocument.getId()).orElseThrow(() -> new EntityNotFoundException("WebDocument not found with ID: " + webDocument.getId())).getName());
            }
            webDocumentRepo.save(webDocument);
            webDocumentList.add(webDocument);
        }
        webAboutUs.getDocuments().clear();
        webAboutUs.getDocuments().addAll(webDocumentList);

        webAboutUsRepo.save(webAboutUs);
    }

    @Override
    public void deleteWebDocument(Long id) {
        WebDocument webDocument = webDocumentRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("WebDocument not found with ID: " + id));
        if (webDocument.getName() != null) {
            try {
                Files.delete(Paths.get("uploaded/webAboutUs/documents/" + webDocument.getName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        webDocumentRepo.delete(webDocument);
    }

    @Override
    public void deleteGalleryImage(Long id) {
        Image image = imageRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Image not found with ID: " + id));
        if (image.getName() != null) {
            try {
                Files.delete(Paths.get("uploaded/webAboutUs/gallery/" + image.getName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        imageRepo.delete(image);
    }

    @Override
    public void deleteExtraGalleryImage(Long id) {
        Image image = imageRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Image not found with ID: " + id));
        if (image.getName() != null) {
            try {
                Files.delete(Paths.get("uploaded/webAboutUs/extra-gallery/" + image.getName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        imageRepo.delete(image);
    }

}
