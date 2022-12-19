package avada.media.myhouse24_admin.model;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import avada.media.myhouse24_admin.model.systemSettings.pages.Service;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table
@Data
public class Counter extends MappedEntity {

    private String uniqueNumber;
    private Date requestedDate;
    private Double amount;
    @ManyToOne
    private Flat flat;
    @ManyToOne
    private Service service;
    private Status status;

    @Getter
    @RequiredArgsConstructor
    public enum Status {

        NEW("Новое"),
        ACTIVE("Учтено"),
        PAY_DONE("Учтено и оплачено"),
        DISABLED("Нулевое");

        private final String title;

    }

}
