package avada.media.myhouse24_admin.controller.website;

//import avada.media.myhouse24_admin.model.websiteModels.Slide;
import avada.media.myhouse24_admin.model.websiteModels.MainPageModel;
import avada.media.myhouse24_admin.model.websiteModels.NextToUs;
import avada.media.myhouse24_admin.repo.websiteRepo.ContactsRepository;
import avada.media.myhouse24_admin.repo.websiteRepo.MainPageModelRepository;
import avada.media.myhouse24_admin.repo.websiteRepo.NextToUsRepository;
import avada.media.myhouse24_admin.service.impl.MainPageAdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class MainPageController {

    private final MainPageModelRepository mainPageModelRepository;
    private final NextToUsRepository nextToUsRepository;
    private final ContactsRepository contactsRepository;
    private  final MainPageAdminServiceImpl mainPageAdminService;


    @GetMapping("main-page-admin")
    public ModelAndView showWebSiteMainPageAdmin() {
        ModelAndView modelAndView = new ModelAndView("/website-templates/main-page-admin");
        ModelAndView modelAndViewRedirect = new ModelAndView("redirect:/main-page-admin");
        if (mainPageModelRepository.findAll().isEmpty()){
            mainPageAdminService.mainPageInit(); // инициализация главной страницы с дефолтными данными
            return modelAndViewRedirect;
        } else {
            List<MainPageModel> mainPageModels = mainPageModelRepository.findAll();
            Long currentId = mainPageModels.get(0).getId();
            Optional<MainPageModel> mainPageModelOptional = mainPageModelRepository.findById(currentId);
            ArrayList<MainPageModel> mainPageAttr = new ArrayList<>();
            mainPageModelOptional.ifPresent(mainPageAttr::add);
            modelAndView.addObject("mainPageAttr", mainPageAttr);
            modelAndView.addObject("nextToUsAttr1", nextToUsRepository.findById(1L).get());
            modelAndView.addObject("nextToUsAttr2", nextToUsRepository.findById(2L).get());
            modelAndView.addObject("nextToUsAttr3", nextToUsRepository.findById(3L).get());
            modelAndView.addObject("nextToUsAttr4", nextToUsRepository.findById(4L).get());
            modelAndView.addObject("nextToUsAttr5", nextToUsRepository.findById(5L).get());
            modelAndView.addObject("nextToUsAttr6", nextToUsRepository.findById(6L).get());

            return modelAndView;
        }
    }




    @PostMapping("main-page-admin")
    public String postSlide(@RequestParam("slide1") MultipartFile slide1,
                            @RequestParam("slide2") MultipartFile slide2,
                            @RequestParam("slide3") MultipartFile slide3,
                            @RequestParam String title,
                            @RequestParam String description,
                            @RequestParam boolean applicationLink,
                            @RequestParam("nextToUsImageLink1") MultipartFile nextToUsImageLink1,
                            @RequestParam String nextToUsTitle1,
                            @RequestParam String nextToUsText1,
                            @RequestParam("nextToUsImageLink2") MultipartFile nextToUsImageLink2,
                            @RequestParam String nextToUsTitle2,
                            @RequestParam String nextToUsText2,
                            @RequestParam("nextToUsImageLink3") MultipartFile nextToUsImageLink3,
                            @RequestParam String nextToUsTitle3,
                            @RequestParam String nextToUsText3,
                            @RequestParam("nextToUsImageLink4") MultipartFile nextToUsImageLink4,
                            @RequestParam String nextToUsTitle4,
                            @RequestParam String nextToUsText4,
                            @RequestParam("nextToUsImageLink5") MultipartFile nextToUsImageLink5,
                            @RequestParam String nextToUsTitle5,
                            @RequestParam String nextToUsText5,
                            @RequestParam("nextToUsImageLink6") MultipartFile nextToUsImageLink6,
                            @RequestParam String nextToUsTitle6,
                            @RequestParam String nextToUsText6,
                            @RequestParam String seoTitle,
                            @RequestParam String seoDescription,
                            @RequestParam String seoKeywords) throws IOException {

       mainPageAdminService.updateManePageAdmin(slide1, slide2, slide3, title, description, applicationLink,
               nextToUsImageLink1, nextToUsTitle1, nextToUsText1, nextToUsImageLink2, nextToUsTitle2, nextToUsText2,
               nextToUsImageLink3, nextToUsTitle3, nextToUsText3, nextToUsImageLink4, nextToUsTitle4, nextToUsText4,
               nextToUsImageLink5, nextToUsTitle5, nextToUsText5, nextToUsImageLink6, nextToUsTitle6, nextToUsText6,
               seoTitle, seoDescription, seoKeywords);
        return "redirect:/main-page-admin";
    }
}
