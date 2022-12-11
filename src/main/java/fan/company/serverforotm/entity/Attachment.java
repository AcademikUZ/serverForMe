package fan.company.serverforotm.entity;

import fan.company.serverforotm.entity.template.AbstractEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Attachment extends AbstractEntity {

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

    @Column(nullable = false)
    private boolean view = false;

    @Column(nullable = false)
    private boolean pdtv = false;


}
