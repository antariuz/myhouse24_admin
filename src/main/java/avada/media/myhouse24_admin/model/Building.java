package avada.media.myhouse24_admin.model;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import avada.media.myhouse24_admin.model.systemSettings.pages.Staff;
import avada.media.myhouse24_admin.model.websiteControl.extra.Image;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
public class Building extends MappedEntity {

    private String title;
    private String address;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private List<Image> images = new ArrayList<>();
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private List<Section> sections = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private List<Floor> floors = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Staff> staff = new ArrayList<>();

}
