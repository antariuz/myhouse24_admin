package avada.media.myhouse24_admin.model.websiteControl.extra;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
public class WebService extends MappedEntity {

    private String title;
    private String image;
    @Column(length = 104857)
    private String description;

}
