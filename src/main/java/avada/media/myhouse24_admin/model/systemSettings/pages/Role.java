package avada.media.myhouse24_admin.model.systemSettings.pages;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import avada.media.myhouse24_admin.model.systemSettings.extra.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Role extends MappedEntity {

    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String title;

    @ElementCollection(targetClass = Permission.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "role_permission", joinColumns = @JoinColumn(name = "role_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "permission")
    private Set<Permission> permissions = new HashSet<>();

}