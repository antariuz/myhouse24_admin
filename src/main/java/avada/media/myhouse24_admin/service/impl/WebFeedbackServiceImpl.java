package avada.media.myhouse24_admin.service.impl;

import avada.media.myhouse24_admin.model.common.Seo;
import avada.media.myhouse24_admin.model.websiteControl.pages.WebFeedback;
import avada.media.myhouse24_admin.repo.SeoRepo;
import avada.media.myhouse24_admin.repo.websiteControl.WebFeedbackRepo;
import avada.media.myhouse24_admin.service.WebFeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebFeedbackServiceImpl implements WebFeedbackService {

    private final WebFeedbackRepo webFeedbackRepo;
    private final SeoRepo seoRepo;

    @Override
    public WebFeedback createInitialWebFeedback() {
        WebFeedback webFeedback = new WebFeedback();
        webFeedback.setTitle("Разработчик системы \"Мой Дом 24\"");
        webFeedback.setDescription("<p style=\"margin-right: 0px; margin-bottom: 10px; margin-left: 0px; color: rgb(51, 51, 51); font-family: &quot;Helvetica Neue&quot;, Helvetica, Arial, sans-serif; font-size: 14px;\">Разработчиком системы&nbsp;<i><span style=\"font-weight: 700;\">\"Мой Дом 24\"</span></i>&nbsp;является компания<span style=\"font-weight: 700;\">&nbsp;AVADA-MEDIA&nbsp;</span>- компания специализирующаяся на разработке web и мобильных приложений разного уровня сложности, но неизменно высокого качества&nbsp;</p><p style=\"margin-right: 0px; margin-bottom: 10px; margin-left: 0px; color: rgb(51, 51, 51); font-family: &quot;Helvetica Neue&quot;, Helvetica, Arial, sans-serif; font-size: 14px;\"><span style=\"font-weight: 700;\"><i></i><i>Официальный сайт компании -&nbsp;</i></span><a target=\"_blank\" rel=\"nofollow\" href=\"https://avada-media.ua/\" style=\"color: rgb(51, 122, 183);\"><span style=\"font-weight: 700;\"><i>https://avada-media.ua/</i></span></a></p>");
        webFeedback.setLink("https://avada-media.ua/");
        webFeedback.setFullName("AVADA-MEDIA");
        webFeedback.setLocation("ул. Космонавтов, 32");
        webFeedback.setAddress("Малиновский р-н, г. Одесса");
        webFeedback.setPhoneNumber("+38 (097) 179-96-63");
        webFeedback.setEmail("info@avada-media.com.ua");
        webFeedback.setMapCode("<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2749.8611266486014!2d30.713265815873395!3d46.43163207638925!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x40c633a651e3fd09%3A0x9331380952bbfd2c!2z0LLRg9C70LjRhtGPINCa0L7RgdC80L7QvdCw0LLRgtGW0LIsIDMyLCDQntC00LXRgdCwLCDQntC00LXRgdGM0LrQsCDQvtCx0LvQsNGB0YLRjCwgNjUwMDA!5e0!3m2!1suk!2sua!4v1524221669656\" width=\"100%\" height=\"800\" frameborder=\"0\" style=\"border:0\" allowfullscreen=\"\"></iframe>");
        Seo seo = new Seo();
        seo.setTitle("SEO title of WebFeedback");
        seo.setDescription("SEO description of WebFeedback");
        seo.setKeywords("SEO keywords of WebFeedback");
        webFeedback.setSeo(seo);
        seoRepo.save(seo);
        webFeedbackRepo.save(webFeedback);
        return webFeedback;
    }

}
