package avada.media.myhouse24_admin.model.websiteControl.extra;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table
public class WebExtraInformation extends MappedEntity {

    private String title;
    @Column(length = 10485760)
    private String description;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "web_extra_information_id")
    private List<Image> gallery = new ArrayList<>();

}
