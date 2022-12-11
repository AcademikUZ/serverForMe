package fan.company.serverforotm.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginDto {

    @NotNull(message = "Username bo'sh bo'lmasin!")
    private String username;

    @NotNull(message = "Password bo'sh bo'lmasin!")
    private String password;

}
