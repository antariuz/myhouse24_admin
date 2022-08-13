package avada.media.myhouse24_admin.service.impl;

import avada.media.myhouse24_admin.model.websiteModels.MainPageModel;
import avada.media.myhouse24_admin.model.websiteModels.NextToUs;
import avada.media.myhouse24_admin.repo.websiteRepo.MainPageModelRepository;
import avada.media.myhouse24_admin.repo.websiteRepo.NextToUsRepository;
import avada.media.myhouse24_admin.service.MainPageAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class MainPageAdminServiceImpl implements MainPageAdminService {


    private String slideUploadPath = "/home/sazbserg/IdeaProjects/MyHouse24/Admin/uploads/mainPageSlides";
    private String nextToUsImageUploadPath = "/home/sazbserg/IdeaProjects/MyHouse24/Admin/uploads/mainPageNextToUsImages";



    private final MainPageModelRepository mainPageModelRepository;
    private final NextToUsRepository nextToUsRepository;


    @Override
    public void mainPageInit() {
            MainPageModel mainPageModel = new MainPageModel();
            List<NextToUs> nextToUsList = new ArrayList<>();
            mainPageModel.setTitle("Заголовок");
            mainPageModel.setDescription("Описание");
            mainPageModel.setApplicationLink(true);
            mainPageModel.setSlide1("init-dom.jpg");
            mainPageModel.setSlide2("init-dom.jpg");
            mainPageModel.setSlide3("init-dom.jpg");

            for (int i=0; i<6; i++){
                NextToUs nextToUs = new NextToUs();
                nextToUs.setIndex((i+1L));
                nextToUs.setTitle("Заголовок блока");
                nextToUs.setText("Магазины, парки, кафе");
                nextToUs.setImageLink("init-park.jpg");
                nextToUsRepository.save(nextToUs);
                nextToUsList.add(nextToUs);
            }

            mainPageModel.setNextToUsList(nextToUsList);
            mainPageModel.setSeoTitle("СЕО заголовок");
            mainPageModel.setSeoDescription("Описание СЕО");
            mainPageModel.setKeyWords("СЕО, ключевые слова");
            mainPageModelRepository.save(mainPageModel);
        }


    @Override
    public void updateManePageAdmin(MultipartFile slide1, MultipartFile slide2, MultipartFile slide3, String title, String description, boolean applicationLink, MultipartFile nextToUsImageLink1, String nextToUsTitle1, String nextToUsText1, MultipartFile nextToUsImageLink2, String nextToUsTitle2, String nextToUsText2, MultipartFile nextToUsImageLink3, String nextToUsTitle3, String nextToUsText3, MultipartFile nextToUsImageLink4, String nextToUsTitle4, String nextToUsText4, MultipartFile nextToUsImageLink5, String nextToUsTitle5, String nextToUsText5, MultipartFile nextToUsImageLink6, String nextToUsTitle6, String nextToUsText6, String seoTitle, String seoDescription, String seoKeywords) throws IOException {
        if (mainPageModelRepository.findAll().isEmpty()) {
            MainPageModel mainPageModel = new MainPageModel();
            List<NextToUs> nextToUsList = new ArrayList<>();
            mainPageModel.setNextToUsList(nextToUsList);

            if ((slide1 != null && !slide1.getOriginalFilename().isEmpty()) ||
                (slide2 != null && !slide2.getOriginalFilename().isEmpty()) ||
                (slide3 != null && !slide3.getOriginalFilename().isEmpty())) {
                File uploadDirForSlides = new File(slideUploadPath);

                if (!uploadDirForSlides.exists()) {
                    uploadDirForSlides.mkdir();
                }
            }

            if ((nextToUsImageLink1 != null && !nextToUsImageLink1.getOriginalFilename().isEmpty()) ||
                (nextToUsImageLink2 != null && !nextToUsImageLink2.getOriginalFilename().isEmpty()) ||
                (nextToUsImageLink3 != null && !nextToUsImageLink3.getOriginalFilename().isEmpty()) ||
                (nextToUsImageLink4 != null && !nextToUsImageLink4.getOriginalFilename().isEmpty()) ||
                (nextToUsImageLink5 != null && !nextToUsImageLink5.getOriginalFilename().isEmpty()) ||
                (nextToUsImageLink6 != null && !nextToUsImageLink6.getOriginalFilename().isEmpty())) {
                File uploadDirForNextToUs = new File(nextToUsImageUploadPath);

                if (!uploadDirForNextToUs.exists()) {
                    uploadDirForNextToUs.mkdir();
                }
            }

            if (slide1 != null && !slide1.getOriginalFilename().isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename1 = uuidFile + "." + slide1.getOriginalFilename();
                slide1.transferTo(new File(slideUploadPath + "/" + resultFilename1));
                mainPageModel.setSlide1(resultFilename1);
            }
            if (slide2 != null && !slide2.getOriginalFilename().isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename2 = uuidFile + "." + slide2.getOriginalFilename();
                slide2.transferTo(new File(slideUploadPath + "/" + resultFilename2));
                mainPageModel.setSlide2(resultFilename2);
            }
            if (slide3 != null && !slide3.getOriginalFilename().isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename3 = uuidFile + "." + slide3.getOriginalFilename();
                slide3.transferTo(new File(slideUploadPath + "/" + resultFilename3));
                mainPageModel.setSlide3(resultFilename3);
            }

            mainPageModel.setTitle(title);
            mainPageModel.setDescription(description);
            mainPageModel.setApplicationLink(applicationLink);

            if (nextToUsImageLink1 != null && !nextToUsImageLink1.getOriginalFilename().isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename1 = uuidFile + "." + nextToUsImageLink1.getOriginalFilename();
                nextToUsImageLink1.transferTo(new File(nextToUsImageUploadPath + "/" + resultFilename1));
                NextToUs nextToUs = new NextToUs();
                nextToUs.setTitle(nextToUsTitle1);
                nextToUs.setText(nextToUsText1);
                nextToUs.setImageLink(resultFilename1);
                mainPageModel.getNextToUsList().add(nextToUs);
            }
            if (nextToUsImageLink2 != null && !nextToUsImageLink2.getOriginalFilename().isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename2 = uuidFile + "." + nextToUsImageLink2.getOriginalFilename();
                nextToUsImageLink2.transferTo(new File(nextToUsImageUploadPath + "/" + resultFilename2));
                NextToUs nextToUs = new NextToUs();
                nextToUs.setTitle(nextToUsTitle2);
                nextToUs.setText(nextToUsText2);
                nextToUs.setImageLink(resultFilename2);
                mainPageModel.getNextToUsList().add(nextToUs);
            }
            if (nextToUsImageLink3 != null && !nextToUsImageLink3.getOriginalFilename().isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename3 = uuidFile + "." + nextToUsImageLink3.getOriginalFilename();
                nextToUsImageLink3.transferTo(new File(nextToUsImageUploadPath + "/" + resultFilename3));
                NextToUs nextToUs = new NextToUs();
                nextToUs.setTitle(nextToUsTitle3);
                nextToUs.setText(nextToUsText3);
                nextToUs.setImageLink(resultFilename3);
                mainPageModel.getNextToUsList().add(nextToUs);
            }
            if (nextToUsImageLink4 != null && !nextToUsImageLink4.getOriginalFilename().isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename4 = uuidFile + "." + nextToUsImageLink4.getOriginalFilename();
                nextToUsImageLink4.transferTo(new File(nextToUsImageUploadPath + "/" + resultFilename4));
                NextToUs nextToUs = new NextToUs();
                nextToUs.setTitle(nextToUsTitle4);
                nextToUs.setText(nextToUsText4);
                nextToUs.setImageLink(resultFilename4);
                mainPageModel.getNextToUsList().add(nextToUs);
            }
            if (nextToUsImageLink5 != null && !nextToUsImageLink5.getOriginalFilename().isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename5 = uuidFile + "." + nextToUsImageLink5.getOriginalFilename();
                nextToUsImageLink5.transferTo(new File(nextToUsImageUploadPath + "/" + resultFilename5));
                NextToUs nextToUs = new NextToUs();
                nextToUs.setTitle(nextToUsTitle5);
                nextToUs.setText(nextToUsText5);
                nextToUs.setImageLink(resultFilename5);
                mainPageModel.getNextToUsList().add(nextToUs);
            }
            if (nextToUsImageLink6 != null && !nextToUsImageLink6.getOriginalFilename().isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename6 = uuidFile + "." + nextToUsImageLink6.getOriginalFilename();
                nextToUsImageLink6.transferTo(new File(nextToUsImageUploadPath + "/" + resultFilename6));
                NextToUs nextToUs = new NextToUs();
                nextToUs.setTitle(nextToUsTitle6);
                nextToUs.setText(nextToUsText6);
                nextToUs.setImageLink(resultFilename6);
                mainPageModel.getNextToUsList().add(nextToUs);
            }

            mainPageModel.setSeoTitle(seoTitle);
            mainPageModel.setSeoDescription(seoDescription);
            mainPageModel.setKeyWords(seoKeywords);
            nextToUsRepository.saveAll(mainPageModel.getNextToUsList());
            mainPageModelRepository.save(mainPageModel);

        } else {
            List<MainPageModel> mainPageModelList = mainPageModelRepository.findAll();
            MainPageModel mainPageModel = mainPageModelList.get(0);

            if ((slide1 != null && !slide1.getOriginalFilename().isEmpty()) ||
                (slide2 != null && !slide2.getOriginalFilename().isEmpty()) ||
                (slide3 != null && !slide3.getOriginalFilename().isEmpty())) {
                File uploadDirForSlides = new File(slideUploadPath);

                if (!uploadDirForSlides.exists()) {
                    uploadDirForSlides.mkdir();
                }
            }

            if ((nextToUsImageLink1 != null && !nextToUsImageLink1.getOriginalFilename().isEmpty()) ||
                (nextToUsImageLink2 != null && !nextToUsImageLink2.getOriginalFilename().isEmpty()) ||
                (nextToUsImageLink3 != null && !nextToUsImageLink3.getOriginalFilename().isEmpty()) ||
                (nextToUsImageLink4 != null && !nextToUsImageLink4.getOriginalFilename().isEmpty()) ||
                (nextToUsImageLink5 != null && !nextToUsImageLink5.getOriginalFilename().isEmpty()) ||
                (nextToUsImageLink6 != null && !nextToUsImageLink6.getOriginalFilename().isEmpty())) {
                File uploadDirForNextToUs = new File(nextToUsImageUploadPath);

                if (!uploadDirForNextToUs.exists()) {
                    uploadDirForNextToUs.mkdir();
                }
            }

            if (slide1 != null && !slide1.getOriginalFilename().isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename1 = uuidFile + "." + slide1.getOriginalFilename();
                slide1.transferTo(new File(slideUploadPath + "/" + resultFilename1));
                mainPageModel.setSlide1(resultFilename1);
            }
            if (slide2 != null && !slide2.getOriginalFilename().isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename2 = uuidFile + "." + slide2.getOriginalFilename();
                slide2.transferTo(new File(slideUploadPath + "/" + resultFilename2));
                mainPageModel.setSlide2(resultFilename2);
            }
            if (slide3 != null && !slide3.getOriginalFilename().isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename3 = uuidFile + "." + slide3.getOriginalFilename();
                slide3.transferTo(new File(slideUploadPath + "/" + resultFilename3));
                mainPageModel.setSlide3(resultFilename3);
            }

            mainPageModel.setTitle(title);
            mainPageModel.setDescription(description);
            mainPageModel.setApplicationLink(applicationLink);

            NextToUs nextToUs1 = nextToUsRepository.findById(1L).get();
            nextToUs1.setTitle(nextToUsTitle1);
            nextToUs1.setText(nextToUsText1);
            if (nextToUsImageLink1 != null && !nextToUsImageLink1.getOriginalFilename().isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename1 = uuidFile + "." + nextToUsImageLink1.getOriginalFilename();
                nextToUsImageLink1.transferTo(new File(nextToUsImageUploadPath + "/" + resultFilename1));
                nextToUs1.setImageLink(resultFilename1);
            }
            nextToUsRepository.save(nextToUs1);

            NextToUs nextToUs2 = nextToUsRepository.findById(2L).get();
            nextToUs2.setTitle(nextToUsTitle2);
            nextToUs2.setText(nextToUsText2);
            if (nextToUsImageLink2 != null && !nextToUsImageLink2.getOriginalFilename().isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename2 = uuidFile + "." + nextToUsImageLink2.getOriginalFilename();
                nextToUsImageLink2.transferTo(new File(nextToUsImageUploadPath + "/" + resultFilename2));
                nextToUs2.setImageLink(resultFilename2);
            }
            nextToUsRepository.save(nextToUs2);

            NextToUs nextToUs3 = nextToUsRepository.findById(3L).get();
            nextToUs3.setTitle(nextToUsTitle3);
            nextToUs3.setText(nextToUsText3);
            if (nextToUsImageLink3 != null && !nextToUsImageLink3.getOriginalFilename().isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename3 = uuidFile + "." + nextToUsImageLink3.getOriginalFilename();
                nextToUsImageLink3.transferTo(new File(nextToUsImageUploadPath + "/" + resultFilename3));
                nextToUs3.setImageLink(resultFilename3);
            }
            nextToUsRepository.save(nextToUs3);

            NextToUs nextToUs4 = nextToUsRepository.findById(4L).get();
            nextToUs4.setTitle(nextToUsTitle4);
            nextToUs4.setText(nextToUsText4);
            if (nextToUsImageLink4 != null && !nextToUsImageLink4.getOriginalFilename().isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename4 = uuidFile + "." + nextToUsImageLink4.getOriginalFilename();
                nextToUsImageLink4.transferTo(new File(nextToUsImageUploadPath + "/" + resultFilename4));
                nextToUs4.setImageLink(resultFilename4);
            }
            nextToUsRepository.save(nextToUs4);

            NextToUs nextToUs5 = nextToUsRepository.findById(5L).get();
            nextToUs5.setTitle(nextToUsTitle5);
            nextToUs5.setText(nextToUsText5);
            if (nextToUsImageLink5 != null && !nextToUsImageLink5.getOriginalFilename().isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename5 = uuidFile + "." + nextToUsImageLink5.getOriginalFilename();
                nextToUsImageLink5.transferTo(new File(nextToUsImageUploadPath + "/" + resultFilename5));
                nextToUs5.setImageLink(resultFilename5);
            }
            nextToUsRepository.save(nextToUs5);

            NextToUs nextToUs6 = nextToUsRepository.findById(6L).get();
            nextToUs6.setTitle(nextToUsTitle6);
            nextToUs6.setText(nextToUsText6);
            if (nextToUsImageLink6 != null && !nextToUsImageLink6.getOriginalFilename().isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename6 = uuidFile + "." + nextToUsImageLink6.getOriginalFilename();
                nextToUsImageLink6.transferTo(new File(nextToUsImageUploadPath + "/" + resultFilename6));
                nextToUs6.setImageLink(resultFilename6);
            }
            nextToUsRepository.save(nextToUs6);

            mainPageModel.setSeoTitle(seoTitle);
            mainPageModel.setSeoDescription(seoDescription);
            mainPageModel.setKeyWords(seoKeywords);

            mainPageModelRepository.save(mainPageModel);
        }
    }
}
