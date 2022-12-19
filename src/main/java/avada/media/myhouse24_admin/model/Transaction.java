package avada.media.myhouse24_admin.model;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import avada.media.myhouse24_admin.model.systemSettings.pages.Staff;
import avada.media.myhouse24_admin.model.systemSettings.pages.TransactionPurpose;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Data
public class Transaction extends MappedEntity {

    private String uniqueNumber;
    private Date requestedDate;
    @Enumerated(EnumType.STRING)
    private Type type;
    private String comment;
    private boolean used;
    private Double amount;
    @ManyToOne
    private TransactionPurpose transactionPurpose;
    @ManyToOne
    private Staff staff;
    @ManyToOne
    private Account account;
    @ManyToOne
    private User user;

    @RequiredArgsConstructor
    @Getter
    public enum Type {
        IN("Приход"),
        OUT("Расход");

        private final String title;
    }

}
