package avada.media.myhouse24_admin.model.web.pages;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import avada.media.myhouse24_admin.model.web.Seo;
import avada.media.myhouse24_admin.model.web.WebService;
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
public class WebServices extends MappedEntity {

    @OneToMany
    private List<WebService> webServiceList = new ArrayList<>();
    @OneToOne
    private Seo seo;

}
