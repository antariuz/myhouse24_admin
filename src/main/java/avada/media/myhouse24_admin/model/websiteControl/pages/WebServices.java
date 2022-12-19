package avada.media.myhouse24_admin.model.websiteControl.pages;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import avada.media.myhouse24_admin.model.common.Seo;
import avada.media.myhouse24_admin.model.websiteControl.extra.WebService;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
public class WebServices extends MappedEntity {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "web_services_id")
    private List<WebService> webServiceList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private Seo seo;

}
