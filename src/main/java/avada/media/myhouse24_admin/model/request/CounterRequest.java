package avada.media.myhouse24_admin.model.request;

import lombok.Data;

@Data
public class CounterRequest {

    private String uniqueNumber;
    private String status;
    private String requestedDate;

    private Long building;
    private String section;
    private Long flat;
    private Long service;

    private Integer pageIndex;
    private Integer pageSize;
    private String sortField;
    private String sortOrder;

}
