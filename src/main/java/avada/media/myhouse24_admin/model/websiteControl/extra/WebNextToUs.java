package avada.media.myhouse24_admin.model.websiteControl.extra;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table
public class WebNextToUs extends MappedEntity {

    private String title;
    @Column(length = 10485760)
    private String description;
    private String image;

}
