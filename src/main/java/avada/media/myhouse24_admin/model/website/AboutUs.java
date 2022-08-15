package avada.media.myhouse24_admin.model.website;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AboutUs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;
    private String description;
    private String directorsPhoto;
    @OneToMany
    private List<PhotoAboutUs> photosAboutUsList;
    private String addInfoTitle;
    private String addInfoDescription;
    @OneToMany
    private List<AdditionalPhotoAboutUs> additionalPhotoAboutUsList;
    @OneToMany
    private List<MyDocument> documents;
    private String seoTitle;
    private String seoDescription;
    private String keyWords;
}
