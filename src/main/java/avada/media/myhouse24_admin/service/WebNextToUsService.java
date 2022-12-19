package avada.media.myhouse24_admin.service;

import avada.media.myhouse24_admin.model.websiteControl.extra.WebNextToUs;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface WebNextToUsService {

    void updateWebNextToUs(List<WebNextToUs> webNextToUsList, List<MultipartFile> nextToUsImages);

}
