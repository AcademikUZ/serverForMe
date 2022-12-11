package fan.company.serverforotm.entity;

import fan.company.serverforotm.entity.template.AbstractEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Division extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    private boolean active = true;

    public Division(String name) {
        this.name = name;
    }

    public Division(boolean active) {
        this.active = active;
    }

}
