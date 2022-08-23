package avada.media.myhouse24_admin.model.websiteControl.pages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @Column(length = 5000)
    private String description;
    private String directorsPhoto;
    private String addInfoTitle;
    @Column(length = 3000)
    private String addInfoDescription;
    private String seoTitle;
    @Column(length = 3000)
    private String seoDescription;
    @Column(length = 500)
    private String seoKeyWords;
}
