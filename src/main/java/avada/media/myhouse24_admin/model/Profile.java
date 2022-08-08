package avada.media.myhouse24_admin.model;

import avada.media.myhouse24_admin.model.common.Image;
import avada.media.myhouse24_admin.model.common.MappedEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table
@Data
@NoArgsConstructor
public class Profile extends MappedEntity {

    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthdate;
    private String notes;
    private String phoneNumber;
    private String viberLogin;
    private String telegramLogin;
    private String profilePicture;

}
