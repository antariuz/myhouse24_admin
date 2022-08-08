package avada.media.myhouse24_admin.model;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
public class Building extends MappedEntity {

    private String title;
    private String address;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private List<Section> sections = new ArrayList<>();
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private List<Floor> floors = new ArrayList<>();
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private List<Staff> staffList = new ArrayList<>();

}
