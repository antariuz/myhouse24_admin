package avada.media.myhouse24_admin.model.website.pages;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import avada.media.myhouse24_admin.model.website.extra.Seo;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table
@Data
public class WebFeedback extends MappedEntity {

    private String title;
    @Column(length = 10485760)
    private String description;
    private String link;

    //  Контакты
    private String fullName;
    private String location;
    private String address;
    private String phoneNumber;
    private String email;

    //  Карта
    @Column(length = 10485760)
    private String mapCode;

    @OneToOne
    private Seo seo;

}
