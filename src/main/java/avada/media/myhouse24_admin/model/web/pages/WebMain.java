package avada.media.myhouse24_admin.model.web.pages;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import avada.media.myhouse24_admin.model.web.Seo;
import avada.media.myhouse24_admin.model.web.WebMainBlock;
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
public class WebMain extends MappedEntity {

    private String slide1;
    private String slide2;
    private String slide3;
    private String title;
    @OneToMany
    private List<WebMainBlock> webMainBlocks = new ArrayList<>();
    @OneToOne
    private Seo seo;

}
