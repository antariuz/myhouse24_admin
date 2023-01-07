package avada.media.myhouse24_admin.model.websiteControl.pages;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import avada.media.myhouse24_admin.model.common.Seo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
public class WebFeedback extends MappedEntity {

    private String title;
    @Column(length = 104857)
    private String description;
    private String link;
    //  Contacts
    private String fullName;
    private String location;
    private String address;
    private String phoneNumber;
    private String email;
    //  Mapcode
    @Column(length = 104857)
    private String mapCode;
    @OneToOne(cascade = CascadeType.ALL)
    private Seo seo;

}
