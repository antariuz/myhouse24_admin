package avada.media.myhouse24_admin.model.request;

import lombok.Data;

@Data
public class MasterRequestRequest {

    private Long id;
    private String requestedDate;
    private Long role;
    private String description;
    private Long flat;
    private Long user;
    private String phoneNumber;
    private Long staff;
    private String status;

    private Integer pageIndex;
    private Integer pageSize;
    private String sortField;
    private String sortOrder;

}
