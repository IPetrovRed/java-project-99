package hexlet.code.specifications;

import hexlet.code.dto.tasks.ParamsDTO;
import hexlet.code.model.Task;
import org.springframework.stereotype.Component;


@Component
public class Specification {
    public org.springframework.data.jpa.domain.Specification<Task> build(ParamsDTO params) {
        return withTitleCont(params.getTitleCont())
                .and(withStatus(params.getStatus()))
                .and(withAssignee(params.getAssigneeId()))
                .and(withLabel(params.getLabelId()));
    }

    private org.springframework.data.jpa.domain.Specification<Task> withTitleCont(String substring) {
        return (root, query, cb) -> substring == null ? cb.conjunction()
                : cb.like(root.get("name"), "%" + substring + "%");
    }

    private org.springframework.data.jpa.domain.Specification<Task> withStatus(String status) {
        return (root, query, cb) -> status == null ? cb.conjunction()
                : cb.equal(root.join("taskStatus").get("slug"), status);
    }

    private org.springframework.data.jpa.domain.Specification<Task> withAssignee(Long assigneeId) {
        return (root, query, cb) -> assigneeId == null ? cb.conjunction()
                : cb.equal(root.join("assignee").get("id"), assigneeId);
    }

    private org.springframework.data.jpa.domain.Specification<Task> withLabel(Long labelId) {
        return (root, query, cb) -> labelId == null ? cb.conjunction()
                : cb.equal(root.join("labels").get("id"), labelId);
    }
}
