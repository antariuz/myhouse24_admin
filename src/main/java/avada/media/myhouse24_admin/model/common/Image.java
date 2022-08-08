package avada.media.myhouse24_admin.model.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Data
@NoArgsConstructor
public class Image extends MappedEntity{

    private String file;

}
