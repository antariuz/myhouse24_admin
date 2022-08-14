package avada.media.myhouse24_admin.model.web.pages;

import avada.media.myhouse24_admin.model.common.Image;
import avada.media.myhouse24_admin.model.common.MappedEntity;
import avada.media.myhouse24_admin.model.web.Seo;
import avada.media.myhouse24_admin.model.web.WebDocument;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
public class WebAbout extends MappedEntity {

    private String title;
    private String description;
    private String bossImage;
    @OneToMany
    private List<Image> gallery = new ArrayList<>();
    private String extraTitle;
    private String extraDescription;
    @OneToMany
    private List<Image> extraGallery = new ArrayList<>();
    @OneToMany
    private List<WebDocument> webDocuments = new ArrayList<>();
    @OneToOne
    private Seo seo;

}
