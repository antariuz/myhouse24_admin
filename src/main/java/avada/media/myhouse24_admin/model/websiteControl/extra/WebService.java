package avada.media.myhouse24_admin.model.websiteControl.extra;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Data
public class WebService extends MappedEntity {

    private String title;
    private String image;
    @Column(length = 10485760)
    private String description;

    @Transient
    public String getImagePath() {
        if (image == null || getId() == null) return null;
        return "/uploaded/webServices/" + image;
    }

}
