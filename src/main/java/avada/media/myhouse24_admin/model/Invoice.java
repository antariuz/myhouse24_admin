package avada.media.myhouse24_admin.model;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table
@Data
@NoArgsConstructor
public class Invoice extends MappedEntity {

    private String number;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate createDate;
    @OneToOne
    private Building building;
    @OneToOne
    private User user;
    @OneToOne
    private Flat flat;
    @OneToOne
    private Account account;
    private Status status;
    @OneToOne
    private Tariff tariff;
    private LocalDate periodStart;
    private LocalDate periodEnd;

    public enum Status {
        PAID,
        PARTLY_PAID,
        UNPAID
    }

}
