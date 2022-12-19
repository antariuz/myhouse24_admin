package avada.media.myhouse24_admin.service.impl;

import avada.media.myhouse24_admin.model.common.FileUtil;
import avada.media.myhouse24_admin.model.websiteControl.extra.Image;
import avada.media.myhouse24_admin.model.websiteControl.extra.WebExtraInformation;
import avada.media.myhouse24_admin.repo.ImageRepo;
import avada.media.myhouse24_admin.repo.websiteControl.WebExtraInformationRepo;
import avada.media.myhouse24_admin.service.WebExtraInformationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebExtraInformationServiceImpl implements WebExtraInformationService {

    private final WebExtraInformationRepo webExtraInformationRepo;
    private final ImageRepo imageRepo;

    @Override
    @Transactional
    public void updateWebExtraInformation(WebExtraInformation webExtraInformation, List<MultipartFile> extraGalleryRequested) {

        List<Image> extraGallery = webExtraInformationRepo.findById(webExtraInformation.getId()).orElseThrow(() -> new EntityNotFoundException("WebExtraInformation not found with ID: " + webExtraInformation.getId())).getGallery();
        if (extraGalleryRequested != null) {
            for (MultipartFile galleryImage : extraGalleryRequested) {
                try {
                    String changedName = "extra-gallery-image-" + (extraGallery.size() + 1) + Objects.requireNonNull(galleryImage.getOriginalFilename()).substring(galleryImage.getOriginalFilename().lastIndexOf("."));
                    FileUtil.saveFile("webAboutUs/extra-gallery", changedName, galleryImage);
                    Image image = new Image();
                    image.setName(changedName);
                    extraGallery.add(image);
                    imageRepo.save(image);
                } catch (IOException e) {
                    log.error("Can't save file. Error: " + e.getMessage());
                }
            }
        }
        webExtraInformation.setGallery(extraGallery);
        webExtraInformationRepo.save(webExtraInformation);
    }

}
