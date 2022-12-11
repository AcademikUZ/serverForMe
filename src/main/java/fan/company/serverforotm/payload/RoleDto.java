package fan.company.serverforotm.payload;

import fan.company.serverforotm.entity.enums.Huquq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleDto {

    @NotBlank  // Probellerni o'tkazmaydi
    private String name; //ADMIN, USER VA BOSHQALAR

    @NotEmpty
    private List<Huquq> huquqList;

    private String description;  // Role nima vazifa bajarishi to'g'risida izoh

}
