package hexlet.code.services;

import hexlet.code.dto.tasks.CreateDTO;
import hexlet.code.dto.tasks.TaskDTO;
import hexlet.code.dto.tasks.ParamsDTO;
import hexlet.code.dto.tasks.UpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mappers.TaskMapper;
import hexlet.code.repositories.LabelRepository;
import hexlet.code.repositories.TaskRepository;
import hexlet.code.repositories.TaskStatusRepository;
import hexlet.code.repositories.UserRepository;
import hexlet.code.specifications.Specification;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final Specification taskSpecification;
    private final UserRepository userRepository;
    private final TaskStatusRepository taskStatusRepository;
    private final LabelRepository labelRepository;


    public TaskDTO create(CreateDTO taskCreateDTO) {
        var task = taskMapper.map(taskCreateDTO);
        taskRepository.save(task);
        return taskMapper.map(task);
    }

    public List<TaskDTO> getAll() {
        return taskRepository.findAll().stream()
                .map(taskMapper::map).toList();
    }

    public List<TaskDTO> getAll(ParamsDTO taskFilterDTO) {
        var filter = taskSpecification.build(taskFilterDTO);
        var tasks = taskRepository.findAll(filter);

        return tasks.stream()
                .map(taskMapper::map)
                .toList();
    }

    public TaskDTO findById(Long id) {
        var task = taskRepository.findByIdWithEagerUpload(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task With Id: " + id + " Not Found"));
        return taskMapper.map(task);
    }

    public TaskDTO update(UpdateDTO taskUpdateDTO, Long id) {
        var task = taskRepository.findByIdWithEagerUpload(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task With Id: " + id + " Not Found"));

        taskMapper.update(taskUpdateDTO, task);

        taskRepository.save(task);
        return taskMapper.map(task);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
