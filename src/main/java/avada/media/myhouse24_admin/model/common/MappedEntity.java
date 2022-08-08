package avada.media.myhouse24_admin.model.common;

import javax.persistence.*;

@MappedSuperclass
public class MappedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

}
