package avada.media.myhouse24_admin.model.website.pages;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Data
public class WebFeedback extends MappedEntity {

    private String title;
    private String description;
    private String link;

    //  Контакты
    private String fullName;


    private String location;
    private String address;
    private String phoneNumber;
    private String email;

    //  Карта
    @Column(length = 1000)
    private String mapCode;

    //  Seo
    private String seoTitle;
    private String seoDescription;
    private String seoKeywords;

}
