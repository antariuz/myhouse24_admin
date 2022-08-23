package avada.media.myhouse24_admin.model.websiteControl.pages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class NextToUs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @PrimaryKeyJoinColumn
    private Long index;

    private String title;

    @Column(length = 3000)
    private String text;

    private String imageLink;

    public Long getId() {
        return id;
    }

    public NextToUs(String title, String text, String imageLink) {
        this.title = title;
        this.text = text;
        this.imageLink = imageLink;
    }
}
