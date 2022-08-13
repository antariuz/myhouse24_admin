package avada.media.myhouse24_admin.model;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
public class Flat extends MappedEntity {

    private String number;
    private String totalSquare;
    @ManyToOne
    private Floor floor;
    @ManyToOne
    private Section section;
    @ManyToOne
    private Building building;
    @ManyToOne
    private User user;
    @ManyToOne
    private Tariff tariff;
    @OneToMany
    private List<Account> account = new ArrayList<>();

}
