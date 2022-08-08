package avada.media.myhouse24_admin.model;

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
public class Account extends MappedEntity {

    private String number;
    private Status status;
    @OneToOne
    private Building building;
    @OneToOne
    private Flat flat;

    public enum Status {
        ACTIVE,
        INACTIVE
    }

}
