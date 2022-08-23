package avada.media.myhouse24_admin.model.systemSettings;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table
@Data
public class TransactionPurpose extends MappedEntity {

    private String name;
    @Enumerated(EnumType.STRING)
    private Type type;
    private boolean isUsed;

    public enum Type {
        IN,
        OUT
    }

}
