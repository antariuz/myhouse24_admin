package avada.media.myhouse24_admin.service;

import avada.media.myhouse24_admin.model.dto.WebMainDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface WebMainService {

    void updateWebMain(Long id, WebMainDTO webMainDTO, List<MultipartFile> slides);

}
