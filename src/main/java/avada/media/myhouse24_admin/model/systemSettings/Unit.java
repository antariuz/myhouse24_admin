package avada.media.myhouse24_admin.model.systemSettings;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Unit extends MappedEntity {

    private String name;

    //добавил это поле, так как требуется проверка по использованию единицы измерения
    //при первом создании единицы измерения объект ещё нигде не используется, потому used = false
    private boolean used = false;

}
