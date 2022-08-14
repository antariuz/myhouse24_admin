package avada.media.myhouse24_admin.model;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import avada.media.myhouse24_admin.model.common.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "_user")
@Data
@NoArgsConstructor
public class User extends MappedEntity {

    private String uniqueId;
    private String email;
    private String password;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();
    @OneToOne(fetch = FetchType.LAZY)
    private Profile profile;
    @OneToMany
    private List<Flat> flats = new ArrayList<>();

}
