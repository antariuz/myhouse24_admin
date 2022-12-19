package avada.media.myhouse24_admin.model;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "_user")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends MappedEntity {

    private String uniqueId;
    private String email;
    private String password;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Profile profile;
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Flat> flats = new ArrayList<>();
    @CreationTimestamp
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private Status status;
    private boolean hasDebt;

}
