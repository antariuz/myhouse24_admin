package avada.media.myhouse24_admin.model.web;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Data
public class WebDocument extends MappedEntity {

    private String file;
    private String fileName;

}
