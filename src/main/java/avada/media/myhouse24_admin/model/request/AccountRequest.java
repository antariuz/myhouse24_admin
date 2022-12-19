package avada.media.myhouse24_admin.model.request;

import lombok.Data;

@Data
public class AccountRequest {

    private String uniqueNumber;
    private String status;
    private Long flat;
    private Long building;
    private String section;
    private Long user;
    private String balance;

    private Integer pageIndex;
    private Integer pageSize;
    private String sortField;
    private String sortOrder;

}
