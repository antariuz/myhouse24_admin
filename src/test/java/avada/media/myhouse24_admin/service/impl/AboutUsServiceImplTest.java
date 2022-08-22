package avada.media.myhouse24_admin.service.impl;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import avada.media.myhouse24_admin.model.website.pages.AboutUs;
import avada.media.myhouse24_admin.model.website.pages.MyDocument;
import avada.media.myhouse24_admin.model.website.pages.PhotoAboutUs;
import avada.media.myhouse24_admin.repo.website.AboutUsRepo;
import avada.media.myhouse24_admin.repo.website.AdditionalPhotoAboutUsRepo;
import avada.media.myhouse24_admin.repo.website.MyDocumentRepo;
import avada.media.myhouse24_admin.repo.website.PhotoAboutUsRepo;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {AboutUsServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AboutUsServiceImplTest {
    @MockBean
    private AboutUsRepo aboutUsRepo;

    @Autowired
    private AboutUsServiceImpl aboutUsServiceImpl;

    @MockBean
    private MyDocumentRepo myDocumentRepo;

    @MockBean
    private PhotoAboutUsRepo photoAboutUsRepo;

    @Test
    void testUpdateAboutUsInfo() throws IOException {
        AboutUs aboutUs = new AboutUs();
        aboutUs.setAddInfoDescription("Add Info Description");
        aboutUs.setAddInfoTitle("Dr");
        aboutUs.setDescription("The characteristics of someone or something");
        aboutUs.setDirectorsPhoto("alice.liddell@example.org");
        aboutUs.setId(123L);
        aboutUs.setSeoDescription("Seo Description");
        aboutUs.setSeoKeyWords("Seo Key Words");
        aboutUs.setSeoTitle("Dr");
        aboutUs.setTitle("Dr");
        Optional<AboutUs> ofResult = Optional.of(aboutUs);

        AboutUs aboutUs1 = new AboutUs();
        aboutUs1.setAddInfoDescription("Add Info Description");
        aboutUs1.setAddInfoTitle("Dr");
        aboutUs1.setDescription("The characteristics of someone or something");
        aboutUs1.setDirectorsPhoto("alice.liddell@example.org");
        aboutUs1.setId(123L);
        aboutUs1.setSeoDescription("Seo Description");
        aboutUs1.setSeoKeyWords("Seo Key Words");
        aboutUs1.setSeoTitle("Dr");
        aboutUs1.setTitle("Dr");
        when(this.aboutUsRepo.save((AboutUs) any())).thenReturn(aboutUs1);
        when(this.aboutUsRepo.findById((Long) any())).thenReturn(ofResult);
        this.aboutUsServiceImpl.updateAboutUsInfo("Dr", "The characteristics of someone or something",
                new MockMultipartFile("Name", "AAAAAAAA".getBytes("UTF-8")), "Dr", "Add Info Description", "Dr",
                "Seo Description", "Seo Key Words");
        verify(this.aboutUsRepo).save((AboutUs) any());
        verify(this.aboutUsRepo).findById((Long) any());
    }



    @Test
    void testUpdateAboutUsInfo3() throws IOException {
        AboutUs aboutUs = new AboutUs();
        aboutUs.setAddInfoDescription("Add Info Description");
        aboutUs.setAddInfoTitle("Dr");
        aboutUs.setDescription("The characteristics of someone or something");
        aboutUs.setDirectorsPhoto("alice.liddell@example.org");
        aboutUs.setId(123L);
        aboutUs.setSeoDescription("Seo Description");
        aboutUs.setSeoKeyWords("Seo Key Words");
        aboutUs.setSeoTitle("Dr");
        aboutUs.setTitle("Dr");
        Optional<AboutUs> ofResult = Optional.of(aboutUs);

        AboutUs aboutUs1 = new AboutUs();
        aboutUs1.setAddInfoDescription("Add Info Description");
        aboutUs1.setAddInfoTitle("Dr");
        aboutUs1.setDescription("The characteristics of someone or something");
        aboutUs1.setDirectorsPhoto("alice.liddell@example.org");
        aboutUs1.setId(123L);
        aboutUs1.setSeoDescription("Seo Description");
        aboutUs1.setSeoKeyWords("Seo Key Words");
        aboutUs1.setSeoTitle("Dr");
        aboutUs1.setTitle("Dr");
        when(this.aboutUsRepo.save((AboutUs) any())).thenReturn(aboutUs1);
        when(this.aboutUsRepo.findById((Long) any())).thenReturn(ofResult);
        this.aboutUsServiceImpl.updateAboutUsInfo("Dr", "The characteristics of someone or something", null, "Dr",
                "Add Info Description", "Dr", "Seo Description", "Seo Key Words");
        verify(this.aboutUsRepo).save((AboutUs) any());
        verify(this.aboutUsRepo).findById((Long) any());
    }


    @Test
    void testUpdateAboutUsInfo5() throws IOException, IllegalStateException {
        AboutUs aboutUs = new AboutUs();
        aboutUs.setAddInfoDescription("Add Info Description");
        aboutUs.setAddInfoTitle("Dr");
        aboutUs.setDescription("The characteristics of someone or something");
        aboutUs.setDirectorsPhoto("alice.liddell@example.org");
        aboutUs.setId(123L);
        aboutUs.setSeoDescription("Seo Description");
        aboutUs.setSeoKeyWords("Seo Key Words");
        aboutUs.setSeoTitle("Dr");
        aboutUs.setTitle("Dr");
        Optional<AboutUs> ofResult = Optional.of(aboutUs);

        AboutUs aboutUs1 = new AboutUs();
        aboutUs1.setAddInfoDescription("Add Info Description");
        aboutUs1.setAddInfoTitle("Dr");
        aboutUs1.setDescription("The characteristics of someone or something");
        aboutUs1.setDirectorsPhoto("alice.liddell@example.org");
        aboutUs1.setId(123L);
        aboutUs1.setSeoDescription("Seo Description");
        aboutUs1.setSeoKeyWords("Seo Key Words");
        aboutUs1.setSeoTitle("Dr");
        aboutUs1.setTitle("Dr");
        when(this.aboutUsRepo.save((AboutUs) any())).thenReturn(aboutUs1);
        when(this.aboutUsRepo.findById((Long) any())).thenReturn(ofResult);
        MultipartFile multipartFile = mock(MultipartFile.class);
        doNothing().when(multipartFile).transferTo((java.io.File) any());
        when(multipartFile.getOriginalFilename()).thenReturn("foo.txt");
        this.aboutUsServiceImpl.updateAboutUsInfo("Dr", "The characteristics of someone or something", multipartFile, "Dr",
                "Add Info Description", "Dr", "Seo Description", "Seo Key Words");
        verify(this.aboutUsRepo).save((AboutUs) any());
        verify(this.aboutUsRepo).findById((Long) any());
        verify(multipartFile, atLeast(1)).getOriginalFilename();
        verify(multipartFile).transferTo((java.io.File) any());
    }


    @Test
    void testSaveNewPhoto() throws IOException {
        PhotoAboutUs photoAboutUs = new PhotoAboutUs();
        photoAboutUs.setId(123L);
        photoAboutUs.setLink("Link");
        when(this.photoAboutUsRepo.save((PhotoAboutUs) any())).thenReturn(photoAboutUs);
        this.aboutUsServiceImpl.saveNewPhoto(new MockMultipartFile("Name", "AAAAAAAA".getBytes("UTF-8")));
        verify(this.photoAboutUsRepo).save((PhotoAboutUs) any());
    }


    @Test
    void testSaveNewPhoto4() throws IOException, IllegalStateException {
        PhotoAboutUs photoAboutUs = new PhotoAboutUs();
        photoAboutUs.setId(123L);
        photoAboutUs.setLink("Link");
        when(this.photoAboutUsRepo.save((PhotoAboutUs) any())).thenReturn(photoAboutUs);
        MockMultipartFile mockMultipartFile = mock(MockMultipartFile.class);
        doNothing().when(mockMultipartFile).transferTo((java.io.File) any());
        when(mockMultipartFile.getOriginalFilename()).thenReturn("foo.txt");
        this.aboutUsServiceImpl.saveNewPhoto(mockMultipartFile);
        verify(this.photoAboutUsRepo).save((PhotoAboutUs) any());
        verify(mockMultipartFile, atLeast(1)).getOriginalFilename();
        verify(mockMultipartFile).transferTo((java.io.File) any());
    }



    @Test
    void testSaveNewDocument() throws IOException {
        MyDocument myDocument = new MyDocument();
        myDocument.setId(123L);
        myDocument.setLink("Link");
        myDocument.setName("Name");
        when(this.myDocumentRepo.save((MyDocument) any())).thenReturn(myDocument);
        this.aboutUsServiceImpl.saveNewDocument("Name", new MockMultipartFile("Name", "AAAAAAAA".getBytes("UTF-8")));
        verify(this.myDocumentRepo).save((MyDocument) any());
    }


    @Test
    void testSaveNewDocument2() throws IOException {
        MyDocument myDocument = new MyDocument();
        myDocument.setId(123L);
        myDocument.setLink("Link");
        myDocument.setName("Name");
        when(this.myDocumentRepo.save((MyDocument) any())).thenReturn(myDocument);
        this.aboutUsServiceImpl.saveNewDocument("Name", null);
        verify(this.myDocumentRepo).save((MyDocument) any());
    }


    @Test
    void testSaveNewDocument4() throws IOException, IllegalStateException {
        MyDocument myDocument = new MyDocument();
        myDocument.setId(123L);
        myDocument.setLink("Link");
        myDocument.setName("Name");
        when(this.myDocumentRepo.save((MyDocument) any())).thenReturn(myDocument);
        MockMultipartFile mockMultipartFile = mock(MockMultipartFile.class);
        doNothing().when(mockMultipartFile).transferTo((java.io.File) any());
        when(mockMultipartFile.getOriginalFilename()).thenReturn("foo.txt");
        this.aboutUsServiceImpl.saveNewDocument("Name", mockMultipartFile);
        verify(this.myDocumentRepo).save((MyDocument) any());
        verify(mockMultipartFile, atLeast(1)).getOriginalFilename();
        verify(mockMultipartFile).transferTo((java.io.File) any());
    }
}

