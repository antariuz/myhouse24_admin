package avada.media.myhouse24_admin.model.websiteModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Contacts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String companyName;
    private String companyAdress;
    private String phoneNumber;
    private String eMail;
    private String title;
    private String shortText;
    private String webSiteLink;
    private String googleMapsCoordinates;

    private String seoTitle;
    private String seoDescription;
    private String keyWords;
}
