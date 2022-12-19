package avada.media.myhouse24_admin.model.dto;

import avada.media.myhouse24_admin.model.Section;
import avada.media.myhouse24_admin.model.systemSettings.extra.Unit;
import lombok.Data;

import java.util.Date;

@Data
public class CounterDTO {

    private Long id;
    private String uniqueNumber;
    private StatusDTO status;
    private Date requestedDate;
    private BuildingDTO building;
    private Section section;
    private FlatDTO flat;

    private ServiceDTO service;
    private Double amount;
    private Unit unit;

}
