package avada.media.myhouse24_admin.model;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Data
@NoArgsConstructor
public class TransactionPurpose extends MappedEntity {

    private String name;
    private Type type;

    public enum Type {
        IN,
        OUT
    }

}
