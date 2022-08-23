package avada.media.myhouse24_admin.controller.websiteControl;

import avada.media.myhouse24_admin.repo.websiteControl.AboutUsRepo;
import avada.media.myhouse24_admin.repo.websiteControl.AdditionalPhotoAboutUsRepo;
import avada.media.myhouse24_admin.repo.websiteControl.MyDocumentRepo;
import avada.media.myhouse24_admin.repo.websiteControl.PhotoAboutUsRepo;
import avada.media.myhouse24_admin.service.impl.AboutUsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class AboutUsController {

    private final AboutUsRepo aboutUsRepo;
    private final PhotoAboutUsRepo photoAboutUsRepo;
    private final AdditionalPhotoAboutUsRepo additionalPhotoAboutUsRepo;
    private final MyDocumentRepo myDocumentRepo;
    private final AboutUsServiceImpl aboutUsService;


    @GetMapping("about-us-admin")
    public ModelAndView showAllDateAboutUs() {
        ModelAndView modelAndView = new ModelAndView("pages/website-control/about-us-admin");
        ModelAndView modelAndViewRedirect = new ModelAndView("redirect:/about-us-admin");
        if (aboutUsRepo.findAll().isEmpty()) {
            aboutUsService.aboutUsPageInit(); // инициализация главной страницы с дефолтными данными
            return modelAndViewRedirect;
        } else {
            modelAndView.addObject("aboutUsAttr", aboutUsRepo.findById(1L).get());
            modelAndView.addObject("additionalPhotoAboutUsListAttr", additionalPhotoAboutUsRepo.findAll());
            modelAndView.addObject("photoAboutUsAttr", photoAboutUsRepo.findAll());
            modelAndView.addObject("myDocumentsAttr", myDocumentRepo.findAll());
            return modelAndView;
        }
    }

    @PostMapping("about-us-admin")
    public String saveAboutUsMainInfo(@RequestParam String title,
                                      @RequestParam String description,
                                      @RequestParam("directorsPhoto") MultipartFile file,
                                      @RequestParam String addInfoTitle,
                                      @RequestParam String addInfoDescription,
                                      @RequestParam String seoTitle,
                                      @RequestParam String seoDescription,
                                      @RequestParam String seoKeyWords,
                                      @RequestParam String name, @RequestParam("link") MultipartFile fileDoc) throws IOException {
        aboutUsService.updateAboutUsInfo(title, description, file, addInfoTitle, addInfoDescription, seoTitle, seoDescription, seoKeyWords);
        aboutUsService.saveNewDocument(name, fileDoc);
        return "redirect:/about-us-admin";
    }


    @PostMapping("about-us-admin/photo-about-us")
    public String savePhotoAboutUs(@RequestParam("photosAboutUs") MultipartFile[] files) throws IOException {
       aboutUsService.saveNewPhotos(files);
        return "redirect:/about-us-admin";
    }

    @GetMapping("about-us-admin/photo/remove/{id}")
    public String removePhotoAboutUs(@PathVariable Long id) {
        photoAboutUsRepo.deleteById(id);;
        return "redirect:/about-us-admin";
    }

    @PostMapping("about-us-admin/additional-photo-about-us")
    public String saveAddPhotoAboutUs(@RequestParam("additionalPhotoAboutUs") MultipartFile[] files) throws IOException {
        aboutUsService.saveNewAddPhotos(files);
        return "redirect:/about-us-admin";
    }


    @GetMapping("about-us-admin/additional-photo/remove/{id}")
    public String removeAddPhotoAboutUs(@PathVariable Long id) {
        additionalPhotoAboutUsRepo.deleteById(id);;
        return "redirect:/about-us-admin";
    }


    @GetMapping("about-us-admin/document-about-us/remove/{id}")
    public String removeDocumentAboutUs(@PathVariable Long id) {
        myDocumentRepo.deleteById(id);;
        return "redirect:/about-us-admin";
    }
}

