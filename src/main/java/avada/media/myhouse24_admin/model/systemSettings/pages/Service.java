package avada.media.myhouse24_admin.model.systemSettings.pages;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import avada.media.myhouse24_admin.model.systemSettings.extra.Unit;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
@Data
@DynamicUpdate
public class Service extends MappedEntity {

    private String name;
    private boolean showInCounters;
    private boolean used;
    @OneToOne
    private Unit unit;

}
