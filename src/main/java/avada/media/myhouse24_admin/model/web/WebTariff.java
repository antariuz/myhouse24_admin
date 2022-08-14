package avada.media.myhouse24_admin.model.web;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Data
public class WebTariff extends MappedEntity {

    private String image;
    private String caption;

}
