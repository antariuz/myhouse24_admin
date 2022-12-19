package avada.media.myhouse24_admin.service.impl;

import avada.media.myhouse24_admin.model.common.FileUtil;
import avada.media.myhouse24_admin.model.websiteControl.extra.Image;
import avada.media.myhouse24_admin.model.websiteControl.extra.WebDocument;
import avada.media.myhouse24_admin.model.websiteControl.pages.WebAboutUs;
import avada.media.myhouse24_admin.repo.ImageRepo;
import avada.media.myhouse24_admin.repo.websiteControl.WebAboutUsRepo;
import avada.media.myhouse24_admin.repo.websiteControl.WebDocumentRepo;
import avada.media.myhouse24_admin.service.WebAboutUsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
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

    //  RE-WORK
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

}
