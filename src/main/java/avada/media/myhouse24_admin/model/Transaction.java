package avada.media.myhouse24_admin.model;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import avada.media.myhouse24_admin.model.systemSettings.TransactionPurpose;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table
@Data
@NoArgsConstructor
public class Transaction extends MappedEntity {

    private Long number;
    private LocalDate dateFrom;
    private Type type;
    private String description;
    private boolean carryOut;
    private Double amount;
    @ManyToOne
    private TransactionPurpose transactionPurpose;
    @ManyToOne
    private Staff staff;
    @ManyToOne
    private Account account;
    @ManyToOne
    private User user;

    public enum Type {
        IN,
        OUT
    }

}
