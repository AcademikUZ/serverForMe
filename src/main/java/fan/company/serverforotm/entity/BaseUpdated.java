package fan.company.serverforotm.entity;

import fan.company.serverforotm.entity.template.AbstractEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class BaseUpdated extends AbstractEntity {

    @Column(nullable = false)
    private boolean updated = false;

    @JoinColumn(unique = true)
    @ManyToOne(optional = false)
    private Division division;

    public BaseUpdated(Division division) {
        this.division = division;
    }
}
