package avada.media.myhouse24_admin.model;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "_user")
@Data
@NoArgsConstructor
public class User extends MappedEntity {

    private String uId;
    private String email;
    private String password;

}
