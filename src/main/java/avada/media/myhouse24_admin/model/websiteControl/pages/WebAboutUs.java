package avada.media.myhouse24_admin.model.websiteControl.pages;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import avada.media.myhouse24_admin.model.common.Seo;
import avada.media.myhouse24_admin.model.websiteControl.extra.Image;
import avada.media.myhouse24_admin.model.websiteControl.extra.WebDocument;
import avada.media.myhouse24_admin.model.websiteControl.extra.WebExtraInformation;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table
public class WebAboutUs extends MappedEntity {

    private String title;
    @Column(length = 10485760)
    private String description;
    private String photoOfDirector;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "web_about_us_id")
    private List<Image> gallery = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    private WebExtraInformation webExtraInformation;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "web_about_us_id")
    private List<WebDocument> documents = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    private Seo seo;

    public String getPhotoOfDirector() {
        return photoOfDirector != null ? "/uploaded/webAboutUs/" + photoOfDirector : null;
    }

}
