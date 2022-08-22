package avada.media.myhouse24_admin.model.website.pages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainPageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;

    @Column(length = 5000)
    private String description;

    private boolean applicationLink;

    private String slide1;
    private String slide2;
    private String slide3;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<NextToUs> nextToUsList;

    private String seoTitle;

    @Column(length = 5000)
    private String seoDescription;

    @Column(length = 1000)
    private String keyWords;

}
