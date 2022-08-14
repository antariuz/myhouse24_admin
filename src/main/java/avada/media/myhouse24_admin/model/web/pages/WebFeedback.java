package avada.media.myhouse24_admin.model.web.pages;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import avada.media.myhouse24_admin.model.web.Seo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
@Data
public class WebFeedback extends MappedEntity {

    private String title;
    private String description;
    private String link;
    //    Контакты
    private String fullName;
    private String location;
    private String address;
    private String phoneNumber;
    private String email;
    //    Карта
    private String map;

    @OneToOne
    private Seo seo;

}
