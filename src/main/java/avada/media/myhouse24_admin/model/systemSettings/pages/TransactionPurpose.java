package avada.media.myhouse24_admin.model.systemSettings.pages;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;

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

    @RequiredArgsConstructor
    public enum Type {
        IN("Приход"),
        OUT("Расход");

        private final String title;
    }

}
