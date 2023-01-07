package avada.media.myhouse24_admin.service;

import avada.media.myhouse24_admin.model.websiteControl.pages.WebAboutUs;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface WebAboutUsService {

    WebAboutUs createInitialWebAboutUs();
    void updateWebAboutUs(WebAboutUs webAboutUs, MultipartFile photoOfDirector, List<MultipartFile> webDocumentsFiles, List<MultipartFile> gallery);
    void deleteWebDocument(Long id);
    void deleteGalleryImage(Long id);
    void deleteExtraGalleryImage(Long id);

}
