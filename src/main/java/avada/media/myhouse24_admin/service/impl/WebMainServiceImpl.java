package avada.media.myhouse24_admin.service.impl;

import avada.media.myhouse24_admin.model.common.FileUtil;
import avada.media.myhouse24_admin.model.common.Seo;
import avada.media.myhouse24_admin.model.dto.WebMainDTO;
import avada.media.myhouse24_admin.model.websiteControl.extra.WebNextToUs;
import avada.media.myhouse24_admin.model.websiteControl.pages.WebMain;
import avada.media.myhouse24_admin.repo.SeoRepo;
import avada.media.myhouse24_admin.repo.websiteControl.WebMainRepo;
import avada.media.myhouse24_admin.repo.websiteControl.WebNextToUsRepo;
import avada.media.myhouse24_admin.service.WebMainService;
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
public class WebMainServiceImpl implements WebMainService {

    private final WebMainRepo webMainRepo;
    private final WebNextToUsRepo webNextToUsRepo;
    private final SeoRepo seoRepo;

    @Override
    @Transactional
    public WebMain createInitialWebMain() {
        WebMain webMain = new WebMain();
        webMain.setTitle("О управляющей компании");
        webMain.setDescription("<p><span style=\"color: rgb(51, 51, 51); font-family: &quot;Helvetica Neue&quot;, Helvetica, Arial, sans-serif; font-size: 14px;\">Жилой комплекс «NORD» расположен&nbsp; на берегу Черного моря в живописном районе Одессы. Оригинальная</span><span style=\"color: rgb(51, 51, 51); font-family: &quot;Helvetica Neue&quot;, Helvetica, Arial, sans-serif; font-size: 14px;\">&nbsp;архи</span><span style=\"color: rgb(51, 51, 51); font-family: &quot;Helvetica Neue&quot;, Helvetica, Arial, sans-serif; font-size: 14px;\">тектура комплекса делает его уникальным объектом не только для Одессы, но и для всей Украины – дом прекрасно вписывается в архитектуру берега и открывает свои жильцам и гостям прекрасный вид на бескрайнее море. Ярким преимуществом жилого комплекса является его месторасположения – удобная транспортная развязка, социальная инфраструктура, парк и прекрасный морской воздух создают все условия для комфортного проживания!</span><br></p>");
        webMain.setShowLinks(true);
        Seo seo = new Seo();
        webMain.setSeo(seo);
        seo.setTitle("SEO title of WebMain");
        seo.setDescription("SEO description of WebMain");
        seo.setKeywords("SEO keywords of WebMain");
        List<WebNextToUs> webNextToUsList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            WebNextToUs webNextToUs = new WebNextToUs();
            webNextToUs.setTitle("Lorem Ipsum");
            webNextToUs.setDescription("Maecenas id rutrum est. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Cras in arcu quis dolor imperdiet imperdiet. Nunc laoreet mi ut orci faucibus pulvinar. Vestibulum in condimentum dui. Praesent commodo accumsan nisi vitae interdum. Sed lorem ante, accumsan quis aliquam eget, dignissim sed risus. Nulla facilisi. Fusce non euismod nisi. Nullam eget dictum lorem. Quisque quis tempor elit. In tortor tortor, sagittis vitae enim a, pellentesque commodo nisi.");
            webNextToUsList.add(webNextToUs);
        }
        webNextToUsRepo.saveAll(webNextToUsList);
        webMain.setWebNextToUsList(webNextToUsList);
        seoRepo.save(seo);
        webMainRepo.save(webMain);
        return webMain;
    }

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
