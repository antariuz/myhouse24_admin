package avada.media.myhouse24_admin.model.dto;

import avada.media.myhouse24_admin.model.systemSettings.extra.Unit;
import lombok.Data;

@Data
public class ServiceDTO {

    private Long id;
    private String name;
    private boolean showInCounters;
    private Unit unit;

}
