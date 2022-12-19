package avada.media.myhouse24_admin.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MasterRequestDTO {

    private Long id;
    private Date requestedDate;
    private String description;
    private String comment;
    private StatusDTO status;
    private RoleDTO role;
    private FlatDTO flat;
    private UserDTO user;
    private String phoneNumber;
    private StaffDTO staff;
    private Date createdAt;

}
