package avada.media.myhouse24_admin.model.websiteControl.pages;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import avada.media.myhouse24_admin.model.common.Seo;
import avada.media.myhouse24_admin.model.websiteControl.extra.WebNextToUs;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table
public class WebMain extends MappedEntity {

    private String slide1;
    private String slide2;
    private String slide3;

    private String title;
    @Column(length = 10485760)
    private String description;
    private boolean showLinks;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "web_main_id")
    private List<WebNextToUs> webNextToUsList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private Seo seo;

}
