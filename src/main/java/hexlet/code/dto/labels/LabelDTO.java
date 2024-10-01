package hexlet.code.dto.labels;

import hexlet.code.model.Label;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabelDTO {
    private Long id;
    private String name;
    private String createdAt;

    public Label toLabel() {
        var label = new Label();
        label.setId(id);
        label.setName(name);
        return label;
    }
}
