package avada.media.myhouse24_admin.model;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table
@Data
public class Profile extends MappedEntity {

    private String firstname;
    private String middleName;
    private String lastname;
    private LocalDate birthdate;
    private String notes;
    private String phoneNumber;
    private String viberLogin;
    private String telegramLogin;
    private String profileImage;

}
