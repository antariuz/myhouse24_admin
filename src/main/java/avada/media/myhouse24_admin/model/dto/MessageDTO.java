package avada.media.myhouse24_admin.model.dto;

import avada.media.myhouse24_admin.model.Floor;
import avada.media.myhouse24_admin.model.Section;
import lombok.Data;

import java.util.Date;

@Data
public class MessageDTO {

    private Long id;
    private String subject;
    private String text;
    private StaffDTO staff;
    private boolean haveDebt;
    private BuildingDTO building;
    private Section section;
    private Floor floor;
    private FlatDTO flat;
    private UserDTO user;
    private Date createdAt;

    private String toWhom;

}
