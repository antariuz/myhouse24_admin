package avada.media.myhouse24_admin.model;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
public class Invoice extends MappedEntity {

    private String number;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate createDate;
    @ManyToOne
    private Building building;
    @ManyToOne
    private User user;
    @ManyToOne
    private Flat flat;
    @ManyToOne
    private Account account;
    private Status status;
    @ManyToOne
    private Tariff tariff;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    @OneToMany
    private List<InvoiceService> invoiceService = new ArrayList<>();

    public enum Status {
        PAID,
        PARTLY_PAID,
        UNPAID
    }

}
