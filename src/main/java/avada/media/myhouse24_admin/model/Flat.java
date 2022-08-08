package avada.media.myhouse24_admin.model;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
@Data
@NoArgsConstructor
public class Flat extends MappedEntity {

    private String number;
    private String totalSquare;
    @OneToOne
    private Building building;
    @OneToOne
    private User user;
    @OneToOne
    private Tariff tariff;
    @OneToOne
    private Account account;

}
