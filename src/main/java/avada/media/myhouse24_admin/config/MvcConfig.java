package avada.media.myhouse24_admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {


    //@Value("${upload}")
    private String slideUploadPath = "/home/sazbserg/uploads/mainPageSlides";
    private String nextToUsImageUploadPath = "/home/sazbserg/uploads/mainPageNextToUsImages";
    private String directorPhotoUploadPath = "/home/sazbserg/uploads/photoDirector";
    private String photoAboutUsUploadPath = "/home/sazbserg/uploads/photoAboutUs";
    private String additionalPhotoAboutUsUploadPath = "/home/sazbserg/uploads/addPhotoAboutUs";
    private String documentAboutUsUploadPath = "/home/sazbserg/uploads/docsAboutUs";


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/js/");
        registry.addResourceHandler("/blocks/**")
                .addResourceLocations("classpath:/blocks/");
        registry.addResourceHandler("/main-page-slide/**")
                .addResourceLocations("file://" + slideUploadPath + "/");
        registry.addResourceHandler("/main-page-next-to-us/**")
                .addResourceLocations("file://" + nextToUsImageUploadPath + "/");
        registry.addResourceHandler("/director-photo/**")
                .addResourceLocations("file://" + directorPhotoUploadPath + "/");
        registry.addResourceHandler("/photo-about-us/**")
                .addResourceLocations("file://" + photoAboutUsUploadPath + "/");
        registry.addResourceHandler("/additional-photo-about-us/**")
                .addResourceLocations("file://" + additionalPhotoAboutUsUploadPath + "/");
        registry.addResourceHandler("/document-about-us/**")
                .addResourceLocations("file://" + documentAboutUsUploadPath + "/");

    }

}
