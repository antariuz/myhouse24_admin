package avada.media.myhouse24_admin.model.website.pages;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Data
public class WebServices extends MappedEntity {

    //  Seo
    private String seoTitle;
    private String seoDescription;
    private String seoKeywords;

}
