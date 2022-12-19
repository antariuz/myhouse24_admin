package avada.media.myhouse24_admin.model.request;

import lombok.Data;

@Data
public class FlatRequest {

    private String number;
    private Long building;
    private String section;
    private String floor;
    private Long user;
    private String balance;

    private Integer pageIndex;
    private Integer pageSize;
    private String sortField;
    private String sortOrder;

}
