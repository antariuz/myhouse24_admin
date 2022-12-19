package avada.media.myhouse24_admin.model.dto;

import avada.media.myhouse24_admin.model.Section;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class InvoiceDTO {

    private Long id;
    private String uniqueNumber;
    private Date requestedDate;
    private BuildingDTO building;
    private Section section;
    private UserDTO user;
    private FlatDTO flat;
    private AccountDTO account;
    private StatusDTO status;
    private TariffDTO tariff;
    private Date periodStart;
    private Date periodEnd;
    private boolean used;
    private List<InvoiceServiceDTO> invoiceServices = new ArrayList<>();

}
