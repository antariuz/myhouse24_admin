package avada.media.myhouse24_admin.model.dto;

import avada.media.myhouse24_admin.model.systemSettings.extra.Unit;
import lombok.Data;

@Data
public class InvoiceServiceDTO {

    private Long id;
    private ServiceDTO service;
    private Double amount;
    private Unit unit;
    private Double unitPrice;
    private Double totalPrice;

}
