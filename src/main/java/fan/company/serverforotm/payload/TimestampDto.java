package fan.company.serverforotm.payload;


import lombok.*;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
public class TimestampDto {

    @NotNull
    private Timestamp start;

    @NotNull
    private Timestamp end;

    private Long divisionId;


}
