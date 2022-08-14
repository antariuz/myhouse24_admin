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
public class Message extends MappedEntity {

    private String title;
    private String text;
    @ManyToMany
    private List<User> users = new ArrayList<>();
    @ManyToOne
    private Staff staff;
    @ManyToOne
    private User user;

}
