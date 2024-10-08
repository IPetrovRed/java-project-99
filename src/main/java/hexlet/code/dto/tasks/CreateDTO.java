package hexlet.code.dto.tasks;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateDTO {

    private Integer index;

    private Long assigneeId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private String status;

    private Set<Long> taskLabelIds = new HashSet<>();

}
