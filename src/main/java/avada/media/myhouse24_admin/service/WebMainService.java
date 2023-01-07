package avada.media.myhouse24_admin.service;

import avada.media.myhouse24_admin.model.dto.WebMainDTO;
import avada.media.myhouse24_admin.model.websiteControl.pages.WebMain;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface WebMainService {

    WebMain createInitialWebMain();
    void updateWebMain(Long id, WebMainDTO webMainDTO, List<MultipartFile> slides);

}
