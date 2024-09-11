package hexlet.code.services;


import hexlet.code.dto.tasks.CreateDTO;
import hexlet.code.dto.tasks.TaskDTO;
import hexlet.code.dto.tasks.UpdateDTO;
import hexlet.code.mappers.TaskMapper;

import hexlet.code.model.Task;
import hexlet.code.repositories.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    @Autowired
    private TaskMapper mapper;

    public List<TaskDTO> getAll(Specification<Task> spec, PageRequest pageRequest) {
        return repository.findAll(spec, pageRequest).map(mapper::map).toList();
    }

    public List<TaskDTO> getAll() {
        return repository.findAll().stream().map(mapper::map).toList();
    }

    public TaskDTO create(CreateDTO taskCreateDTO) {

        var task = mapper.map(taskCreateDTO);
        repository.save(task);
        return mapper.map(task);
    }

    public TaskDTO findById(Long id) {
        return mapper.map(repository.findById(id)
                .orElseThrow());
    }

    public TaskDTO update(UpdateDTO taskUpdateDTO, Long id) {
        var task = repository.findById(id)
                .orElseThrow();
        mapper.update(taskUpdateDTO, task);
        repository.save(task);
        return mapper.map(task);

    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
