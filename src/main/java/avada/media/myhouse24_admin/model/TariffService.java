package avada.media.myhouse24_admin.model;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
@Data
@NoArgsConstructor
public class TariffService extends MappedEntity {

    @ManyToOne
    private Service service;
    private Double price;

}
