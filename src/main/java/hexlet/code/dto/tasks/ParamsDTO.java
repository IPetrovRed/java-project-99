package hexlet.code.dto.tasks;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParamsDTO {
    private String titleCont;
    private Long assigneeId;
    private String status;
    private Long labelId;
}
