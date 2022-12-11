package fan.company.serverforotm.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DivisionDto {

    @NotNull(message = "name bo'sh bo'lmasin!")
    private String name;

    private boolean active = true;


}
