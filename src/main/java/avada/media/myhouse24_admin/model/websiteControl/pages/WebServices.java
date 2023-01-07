package avada.media.myhouse24_admin.model.websiteControl.pages;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import avada.media.myhouse24_admin.model.common.Seo;
import avada.media.myhouse24_admin.model.websiteControl.extra.WebService;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
public class WebServices extends MappedEntity {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "web_services_id")
    private List<WebService> webServiceList = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    private Seo seo;

}
