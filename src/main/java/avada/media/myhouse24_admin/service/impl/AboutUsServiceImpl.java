package avada.media.myhouse24_admin.service.impl;

import avada.media.myhouse24_admin.model.websiteControl.pages.AboutUs;
import avada.media.myhouse24_admin.model.websiteControl.pages.AdditionalPhotoAboutUs;
import avada.media.myhouse24_admin.model.websiteControl.pages.MyDocument;
import avada.media.myhouse24_admin.model.websiteControl.pages.PhotoAboutUs;
import avada.media.myhouse24_admin.repo.websiteControl.AboutUsRepo;
import avada.media.myhouse24_admin.repo.websiteControl.AdditionalPhotoAboutUsRepo;
import avada.media.myhouse24_admin.repo.websiteControl.MyDocumentRepo;
import avada.media.myhouse24_admin.repo.websiteControl.PhotoAboutUsRepo;
import avada.media.myhouse24_admin.service.AboutUsServise;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AboutUsServiceImpl implements AboutUsServise {

    private final AboutUsRepo aboutUsRepo;
    private final PhotoAboutUsRepo photoAboutUsRepo;
    private final AdditionalPhotoAboutUsRepo additionalPhotoAboutUsRepo;
    private final MyDocumentRepo myDocumentRepo;

    private String directorPhotoUploadPath = "/home/sazbserg/uploads/photoDirector";
    private String photoAboutUsUploadPath = "/home/sazbserg/uploads/photoAboutUs";
    private String additionalPhotoAboutUsUploadPath = "/home/sazbserg/uploads/addPhotoAboutUs";
    private String documentAboutUsUploadPath = "/home/sazbserg/uploads/docsAboutUs";


    @Override
    public void aboutUsPageInit() {
        AboutUs aboutUs = new AboutUs();
        aboutUs.setTitle("О управляющей компании");
        aboutUs.setDescription("Компания-застройщик не только создала этот объект для своих покупателей, но и после окончания строительства взяла на себя все услуги по обеспечению удобного проживания своих жильцов. Для этого была организована управляющая компания «Морская симфония», которая осуществляет комплексное обслуживание коммунальной сферы жизнедеятельности своих клиентов.\n" +
                               "\n" + "Миссией управляющей компании является такая схема взаимодействия с жильцами дома, при которой услуги компании не отвлекают их от проживания и отдыха и создают максимально комфортную экосистему в доме. Незаметный сервис и максимально эффективная работа являются визитной карточкой компании.\n" +
                               "\n" + "\n" + "В функции управляющей компании входят как содержание и управление хозяйством комплекса, так и охраняемой придомовой территории – уборка, техническое обслуживание лифтового хозяйства, системы электро и водоснабжения дома, котельной и пожарной систем, диспетчеризация, охрана, содержание придомовой территории и многое другое. В штате компании собраны только профессионалы своего дела, которые помогут жильцам в самых трудных и критических ситуациях решить их жилищно-коммунальные проблемы семь дней в неделю круглосуточно – электрики и сантехники, инженеры, монтеры и другие специалисты имеют многолетний опыт и высокий уровень квалификации.\n" + "\n" + "Все это делает жилой комплекс  уникальным, комфортным и безопасным местом для проживания и отдыха всей семьи круглый год. Доверьте заботу о вашем доме и вашей квартире управляющей компании  и вы сможете круглый год наслаждаться проживанием на берегу моря и прекрасной природой Одессы.");
        aboutUs.setDirectorsPhoto("init-director.jpg");
        aboutUs.setAddInfoTitle("Дополнительные услуги");
        aboutUs.setAddInfoDescription("Главной обязанностью управляющей компании является поддержка комфортных условий для жильцов, оперативное реагирование на их просьбы и пожелания\n" + "\n" + "К дополнительным услугам управляющей компании также относятся: 24-часовая охрана придомовой территории и паркинга, уборка территории и вывоз мусора, обслуживание пирса и пляжа. Также при желании жильцы дома могут заказать любые строительно-ремонтные работы от штатной бригады компании.");
        aboutUs.setSeoTitle("CRM-система для управляющей компании ЖКХ - \"МОЙ ДОМ 24\"");
        aboutUs.setSeoDescription("Система учета услуг и расходов для жильцов многоквартирных домов - crm для  упавляющей компании ЖКХ и ОСМД. Система \"Мой Дом 24\" состоит из ПЯТИ компонентов:  сайт управляющей компании,  панель администратора,  персональные кабинеты жильцов дома,  мобильное приложение для IPhone и  Android");
        aboutUs.setSeoKeyWords("Система Мой Дом 24,  сайт управляющей компании,  панель администратора,  персональные кабинеты жильцов дома,  мобильное приложение для IPhone,  мобильное приложение для Android, црм для ОСМД, управление квартирами для ОСМД, система управляющей компании жкх");
        aboutUsRepo.save(aboutUs);

        PhotoAboutUs photoAboutUs = new PhotoAboutUs();
        photoAboutUs.setLink("init-photo.jpg");
        photoAboutUsRepo.save(photoAboutUs);

        AdditionalPhotoAboutUs additionalPhotoAboutUs = new AdditionalPhotoAboutUs();
        additionalPhotoAboutUs.setLink("init-photo.jpg");
        additionalPhotoAboutUsRepo.save(additionalPhotoAboutUs);

        MyDocument myDocument = new MyDocument();
       // myDocument.setName("Публичный договор.");
       // myDocument.setLink("init-doc.pdf");
        myDocumentRepo.save(myDocument);
    }

    @Override
    public void updateAboutUsInfo(String title, String description, MultipartFile file, String addInfoTitle, String addInfoDescription, String seoTitle, String seoDescription, String seoKeyWords) throws IOException {
        AboutUs aboutUs = aboutUsRepo.findById(1L).get();
        aboutUs.setTitle(title);
        aboutUs.setDescription(description);

        if ((file != null && !file.getOriginalFilename().isEmpty())) {
            File uploadDirForPhotoDirector = new File(directorPhotoUploadPath);

            if (!uploadDirForPhotoDirector.exists()) {
                uploadDirForPhotoDirector.mkdir();
            }
        }
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(directorPhotoUploadPath + "/" + resultFilename));
            aboutUs.setDirectorsPhoto(resultFilename);
        }

        aboutUs.setAddInfoTitle(addInfoTitle);
        aboutUs.setAddInfoDescription(addInfoDescription);
        aboutUs.setSeoTitle(seoTitle);
        aboutUs.setSeoDescription(seoDescription);
        aboutUs.setSeoKeyWords(seoKeyWords);
        aboutUsRepo.save(aboutUs);
    }

    @Override
    public void saveNewPhoto(MultipartFile file) throws IOException {
        PhotoAboutUs photoAboutUs = new PhotoAboutUs();
        if ((file != null && !file.getOriginalFilename().isEmpty())) {
            File uploadDirForPhotos = new File(photoAboutUsUploadPath);

            if (!uploadDirForPhotos.exists()) {
                uploadDirForPhotos.mkdir();
            }
        }
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(photoAboutUsUploadPath + "/" + resultFilename));
            photoAboutUs.setLink(resultFilename);
        }
        photoAboutUsRepo.save(photoAboutUs);
    }

    @Override
    public void saveNewPhotos(MultipartFile[] files) throws IOException {
        if ((files != null)) {
            File uploadDirForPhotos = new File(photoAboutUsUploadPath);

            if (!uploadDirForPhotos.exists()) {
                uploadDirForPhotos.mkdir();
            }
        }
        for (MultipartFile file: files){
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                PhotoAboutUs photoAboutUs = new PhotoAboutUs();
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                file.transferTo(new File(photoAboutUsUploadPath + "/" + resultFilename));
                photoAboutUs.setLink(resultFilename);
                photoAboutUsRepo.save(photoAboutUs);
            }
        }
    }

    @Override
    public void saveNewAddPhotos(MultipartFile[] files) throws IOException {
        if ((files != null)) {
            File uploadDirForPhotos = new File(additionalPhotoAboutUsUploadPath);

            if (!uploadDirForPhotos.exists()) {
                uploadDirForPhotos.mkdir();
            }
        }
        for (MultipartFile file: files){
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                AdditionalPhotoAboutUs additionalPhotoAboutUs = new AdditionalPhotoAboutUs();
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                file.transferTo(new File(additionalPhotoAboutUsUploadPath + "/" + resultFilename));
                additionalPhotoAboutUs.setLink(resultFilename);
                additionalPhotoAboutUsRepo.save(additionalPhotoAboutUs);
            }
        }
    }

    @Override
    public void saveNewDocument(String name, MultipartFile file) throws IOException {
        MyDocument myDocument = new MyDocument();
        myDocument.setName(name);
        if ((file != null && !file.getOriginalFilename().isEmpty())) {
            File uploadDirForPhotos = new File(documentAboutUsUploadPath);

            if (!uploadDirForPhotos.exists()) {
                uploadDirForPhotos.mkdir();
            }
        }
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(documentAboutUsUploadPath + "/" + resultFilename));
            myDocument.setLink(resultFilename);
        }
        myDocumentRepo.save(myDocument);
    }
}

