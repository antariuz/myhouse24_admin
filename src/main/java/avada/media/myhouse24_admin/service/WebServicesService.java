package avada.media.myhouse24_admin.service;

import avada.media.myhouse24_admin.model.websiteControl.extra.WebService;
import org.springframework.web.multipart.MultipartFile;

public interface WebServicesService {

    void createInitialWebServices();
    void saveWebservice(WebService webService, MultipartFile image);

}
