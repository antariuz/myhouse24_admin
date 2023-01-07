package avada.media.myhouse24_admin.model.request;

import lombok.Data;

@Data
public class MessageRequest {

    private String subject;
    private Integer pageIndex;
    private Integer pageSize;

}
