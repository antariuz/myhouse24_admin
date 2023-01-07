package avada.media.myhouse24_admin.model;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import avada.media.myhouse24_admin.model.systemSettings.pages.Staff;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
public class Message extends MappedEntity {

    private String subject;
    @Column(length = 104857)
    private String text;
    @ManyToMany
    @JoinTable(
            name = "message_user",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();
    @ManyToOne
    private Staff staff;
    private String toWhom;
    @OneToMany(
            mappedBy = "message",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MessageNotification> messageNotifications = new ArrayList<>();
    @CreationTimestamp
    private Date createdAt;

}
