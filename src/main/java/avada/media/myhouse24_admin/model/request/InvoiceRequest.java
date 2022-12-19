package avada.media.myhouse24_admin.model.request;

import lombok.Data;

@Data
public class InvoiceRequest {

    private String uniqueNumber;
    private String status;
    private String requestedDate;
    private String requestedMonth;

    private Long flat;
    private Long user;
    private String used;

    private Integer pageIndex;
    private Integer pageSize;
    private String sortField;
    private String sortOrder;

}
