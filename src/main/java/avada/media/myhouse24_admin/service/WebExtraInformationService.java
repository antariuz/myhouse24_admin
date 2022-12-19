package avada.media.myhouse24_admin.service;

import avada.media.myhouse24_admin.model.websiteControl.extra.WebExtraInformation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface WebExtraInformationService {

    void updateWebExtraInformation(WebExtraInformation webExtraInformation, List<MultipartFile> extraGallery);

}
