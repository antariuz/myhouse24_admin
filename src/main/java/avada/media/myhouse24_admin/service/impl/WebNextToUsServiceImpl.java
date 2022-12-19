package avada.media.myhouse24_admin.service.impl;

import avada.media.myhouse24_admin.model.common.FileUtil;
import avada.media.myhouse24_admin.model.websiteControl.extra.WebNextToUs;
import avada.media.myhouse24_admin.repo.websiteControl.WebNextToUsRepo;
import avada.media.myhouse24_admin.service.WebNextToUsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebNextToUsServiceImpl implements WebNextToUsService {

    private final WebNextToUsRepo webNextToUsRepo;

    @Override
    public void updateWebNextToUs(List<WebNextToUs> webNextToUsListRequested, List<MultipartFile> nextToUsImages) {

        String uploadDir = "/webMain";

        List<WebNextToUs> webNextToUsList = webNextToUsRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
        for (int i = 0; i < webNextToUsList.size(); i++) {
            WebNextToUs webNextToUs = webNextToUsList.get(i);
            webNextToUs.setTitle(webNextToUsListRequested.get(i).getTitle());
            webNextToUs.setDescription(webNextToUsListRequested.get(i).getDescription());
            if (!Objects.equals(nextToUsImages.get(i).getOriginalFilename(), "empty")) {
                try {
                    String changedName = "next-to-us-image-" + (i + 1) + "." + Objects.requireNonNull(nextToUsImages.get(i).getOriginalFilename()).split("\\.")[1];
                    FileUtil.saveFile(uploadDir, changedName, nextToUsImages.get(i));
                    webNextToUs.setImage(changedName);
                } catch (IOException e) {
                    log.error("Can't save image. Error: " + e.getMessage());
                }
            }
        }
        webNextToUsRepo.saveAll(webNextToUsList);
    }

}
