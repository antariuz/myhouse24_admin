package avada.media.myhouse24_admin.model;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table
@Data
@NoArgsConstructor
public class MasterRequest extends MappedEntity {

    private MasterType masterType;
    private String description;
    private LocalDate dateRequest;
    private Status status;
    @ManyToOne
    private Flat flat;
    @ManyToOne
    private Staff staff;

    public enum MasterType {
        ELECTRICIAN,
        ANY
    }

    public enum Status {
        NEW,
        IN_WORK,
        DONE
    }

}
