package hexlet.code.services;

import hexlet.code.dto.taskStatuses.CreateDTO;
import hexlet.code.dto.taskStatuses.DTO;
import hexlet.code.dto.taskStatuses.UpdateDTO;

import hexlet.code.mappers.TaskStatusMapper;
import hexlet.code.repositories.TaskStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskStatusService {

    @Autowired
    private TaskStatusRepository repository;

    @Autowired
    private TaskStatusMapper mapper;

    public List<DTO> getAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest).map(mapper::map).toList();

    }

    public List<DTO> getAll() {
        return repository.findAll().stream().map(mapper::map).toList();
    }

    public DTO create(CreateDTO createDTO) {

        var taskStatus = mapper.map(createDTO);
        repository.save(taskStatus);
        return mapper.map(taskStatus);
    }

    public DTO findById(Long id) {
        return mapper.map(repository.findById(id)
                .orElseThrow());
    }

    public DTO update(UpdateDTO updateDTO, Long id) {
        var taskStatus = repository.findById(id)
                .orElseThrow();
        mapper.update(updateDTO, taskStatus);
        repository.save(taskStatus);
        return mapper.map(taskStatus);

    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
