package avada.media.myhouse24_admin.service.impl;

import avada.media.myhouse24_admin.model.common.FileUtil;
import avada.media.myhouse24_admin.model.dto.WebMainDTO;
import avada.media.myhouse24_admin.model.websiteControl.pages.WebMain;
import avada.media.myhouse24_admin.repo.websiteControl.WebMainRepo;
import avada.media.myhouse24_admin.service.WebMainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Slf4j
public class WebMainServiceImpl implements WebMainService {

    private final WebMainRepo webMainRepo;

    @Override
    public void updateWebMain(Long id, WebMainDTO webMainDTO, List<MultipartFile> slides) {
        WebMain webMain = webMainRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("WebMain not found with ID: " + id));
        webMain.setTitle(webMainDTO.getTitle());
        webMain.setDescription(webMainDTO.getDescription());
        webMain.setShowLinks(webMainDTO.isShowLinks());
        String uploadDir = "/webMain";
        for (int i = 0; i < slides.size(); i++) {
            if (!Objects.equals(slides.get(i).getOriginalFilename(), "empty")) {
                try {
                    String changedName = "slide-" + (i + 1) + "." + Objects.requireNonNull(slides.get(i).getOriginalFilename()).split("\\.")[1];
                    FileUtil.saveFile(uploadDir, changedName, slides.get(i));
                    switch (i) {
                        case 0:
                            webMain.setSlide1(changedName);
                            break;
                        case 1:
                            webMain.setSlide2(changedName);
                            break;
                        case 2:
                            webMain.setSlide3(changedName);
                            break;
                    }
                } catch (IOException e) {
                    log.error("Can't save image. Error: " + e.getMessage());
                }
            }
        }
        webMainRepo.save(webMain);
    }

}
