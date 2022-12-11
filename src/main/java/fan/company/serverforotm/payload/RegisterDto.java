package fan.company.serverforotm.payload;


import fan.company.serverforotm.utils.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterDto {

    @NotNull(message = "FullName bo'sh bo'lmasin!")
    private String fullName;

    @NotNull(message = "Username bo'sh bo'lmasin!")
    private String username;

    @NotNull(message = "Password bo'sh bo'lmasin!")
    private String password;

    @NotNull(message = "PrePassword bo'sh bo'lmasin!")
    private String prePassword;

    @NotNull(message = "Division bo'sh bo'lmasin!")
    private Long divisionId;

    private Long roleId;


}
