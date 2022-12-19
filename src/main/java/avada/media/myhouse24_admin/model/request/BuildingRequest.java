package avada.media.myhouse24_admin.model.request;

import lombok.Data;

@Data
public class BuildingRequest {

    private String title;
    private String address;

    private Integer pageIndex;
    private Integer pageSize;
    private String sortField;
    private String sortOrder;

}
