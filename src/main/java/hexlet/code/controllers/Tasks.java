package hexlet.code.controllers;

import hexlet.code.dto.tasks.CreateDTO;
import hexlet.code.dto.tasks.TaskDTO;
import hexlet.code.dto.tasks.ParamsDTO;
import hexlet.code.dto.tasks.UpdateDTO;
import hexlet.code.services.TaskService;

import hexlet.code.specifications.Specification;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class Tasks {

    @Autowired
    private TaskService taskService;

    @Autowired
    private Specification specification;

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAll(
            ParamsDTO paramsDTO,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "1000") Integer pageSize) {
        var spec = specification.build(paramsDTO);
        var pageable = PageRequest.of(page - 1, pageSize);
        var result = taskService.getAll(spec, pageable);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(taskService.getAll().size()))
                .body(result);
    }

    @GetMapping("/{id}")
    public TaskDTO getById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO create(@Valid @RequestBody CreateDTO createDTO) {
        return taskService.create(createDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO updateById(
            @Valid @RequestBody UpdateDTO taskStatusUpdateDTO,
            @PathVariable Long id
    ) {
        return taskService.update(taskStatusUpdateDTO, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroyById(@PathVariable Long id) {
        taskService.delete(id);
    }
}
