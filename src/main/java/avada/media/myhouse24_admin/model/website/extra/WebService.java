package avada.media.myhouse24_admin.model.website.extra;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import avada.media.myhouse24_admin.model.website.pages.WebServices;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
@Data
public class WebService extends MappedEntity {

    @ManyToOne
    @JoinColumn(name = "web_services_id", nullable = false)
    private WebServices webServices;

    private String image;
    private String title;
    private String description;

}
