package avada.media.myhouse24_admin.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MainPageAdminService {

    void mainPageInit();

    void updateManePageAdmin(MultipartFile slide1, MultipartFile slide2, MultipartFile slide3, String title, String description, boolean applicationLink, MultipartFile nextToUsImageLink1, String nextToUsTitle1, String nextToUsText1, MultipartFile nextToUsImageLink2, String nextToUsTitle2, String nextToUsText2, MultipartFile nextToUsImageLink3, String nextToUsTitle3, String nextToUsText3, MultipartFile nextToUsImageLink4, String nextToUsTitle4, String nextToUsText4, MultipartFile nextToUsImageLink5, String nextToUsTitle5, String nextToUsText5, MultipartFile nextToUsImageLink6, String nextToUsTitle6, String nextToUsText6, String seoTitle, String seoDescription, String seoKeywords) throws IOException;
}
