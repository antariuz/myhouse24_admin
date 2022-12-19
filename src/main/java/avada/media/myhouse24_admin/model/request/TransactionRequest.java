package avada.media.myhouse24_admin.model.request;

import lombok.Data;

@Data
public class TransactionRequest {

    private String uniqueNumber;
    private String requestedDate;
    private String used;
    private Long transactionPurpose;
    private Long user;
    private Long account;
    private String type;

    private Integer pageIndex;
    private Integer pageSize;
    private String sortField;
    private String sortOrder;

}
