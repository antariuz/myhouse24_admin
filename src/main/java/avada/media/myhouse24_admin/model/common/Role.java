package avada.media.myhouse24_admin.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Role extends MappedEntity {

    @Column(unique = true)
    private String name;

}