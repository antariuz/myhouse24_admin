package avada.media.myhouse24_admin.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AboutUsServise {
    void aboutUsPageInit();

    void updateAboutUsInfo(String title, String description, MultipartFile file, String addInfoTitle, String addInfoDescription, String seoTitle, String seoDescription, String seoKeyWords) throws IOException;

    void saveNewPhotos(MultipartFile[] files) throws IOException;

    void saveNewPhoto(MultipartFile file) throws IOException;

    void saveNewAddPhotos(MultipartFile[] files) throws IOException;

    void saveNewDocument(String name, MultipartFile file) throws IOException;

    }
