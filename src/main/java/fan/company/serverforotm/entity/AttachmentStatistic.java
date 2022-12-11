package fan.company.serverforotm.entity;

import fan.company.serverforotm.entity.template.AbstractEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class AttachmentStatistic extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false)
    private String contentType;

    @ManyToOne(optional = false)
    private Division toDivision;

    @ManyToOne(optional = false)
    private Division fromDivision;

    private Long attachmentId;

    private Timestamp pdtvTime;

    @ManyToOne
    private Users pdtvUser;

    public AttachmentStatistic(String name, String originalName, Long size, String contentType, Division toDivision, Division fromDivision, Long attachmentId) {
        this.name = name;
        this.originalName = originalName;
        this.size = size;
        this.contentType = contentType;
        this.toDivision = toDivision;
        this.fromDivision = fromDivision;
        this.attachmentId = attachmentId;
    }
}
