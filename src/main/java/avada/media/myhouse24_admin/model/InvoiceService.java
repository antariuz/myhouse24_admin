package avada.media.myhouse24_admin.model;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
@Data
@NoArgsConstructor
public class InvoiceService extends MappedEntity {

    @ManyToOne
    private Service service;
    private Double amount;
    @ManyToOne
    private Unit unit;
    private Double unitPrice;
    private Double totalPrice;

}
