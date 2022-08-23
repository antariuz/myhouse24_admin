package avada.media.myhouse24_admin.model.system;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
@Data
@NoArgsConstructor
public class Service extends MappedEntity {

    private String name;
    private boolean showInCounters = false;
    private boolean used = false;
    @OneToOne
    private Unit unit;

}
