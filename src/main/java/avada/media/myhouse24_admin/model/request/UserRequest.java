package avada.media.myhouse24_admin.model.request;

import lombok.Data;

@Data
public class UserRequest {

    private String uniqueId;
    private String email;

    private Long building;
    private Long section;
    private Long floor;
    private Long flat;

    private String fullName;
    private String phoneNumber;
    private Boolean hasDebt;
    private String createdAt;
    private String status;

    private Long account;

    private Integer pageIndex;
    private Integer pageSize;
    private String sortField;
    private String sortOrder;

}
