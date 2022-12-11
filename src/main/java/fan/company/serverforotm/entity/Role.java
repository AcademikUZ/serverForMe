package fan.company.serverforotm.entity;

import fan.company.serverforotm.entity.enums.Huquq;
import fan.company.serverforotm.entity.template.AbstractEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Role extends AbstractEntity {

    @Column(nullable = false)
    private String name; //ADMIN, USER VA BOSHQALAR

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Huquq> huquqList;

    @Column(columnDefinition = "text")
    private String description;  // Role nima vazifa bajarishi to'g'risida izoh
}
