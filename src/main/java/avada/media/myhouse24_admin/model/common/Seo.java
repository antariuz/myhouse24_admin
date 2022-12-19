package avada.media.myhouse24_admin.model.common;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Data
public class Seo extends MappedEntity {

    private String title;
    @Column(length = 10485760)
    private String description;
    @Column(length = 10485760)
    private String keywords;

}
