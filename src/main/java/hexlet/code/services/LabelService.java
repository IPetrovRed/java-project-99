package hexlet.code.services;

import hexlet.code.dto.labels.CreateDTO;
import hexlet.code.dto.labels.DTO;
import hexlet.code.dto.labels.UpdateDTO;
import hexlet.code.mappers.LabelMapper;
import hexlet.code.repositories.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelService {

    @Autowired
    private LabelRepository repository;

    @Autowired
    private LabelMapper mapper;

    public List<DTO> getAll() {
        return mapper.map(repository.findAll());
    }

    public DTO create(CreateDTO createDTO) {
        var label = mapper.map(createDTO);
        repository.save(label);
        return mapper.map(label);
    }

    public DTO findById(Long id) {
        return mapper.map(repository.findById(id)
                .orElseThrow());
    }

    public DTO update(UpdateDTO updateDTO, Long id) {
        var label = repository.findById(id)
                .orElseThrow();
        mapper.update(updateDTO, label);
        repository.save(label);
        return mapper.map(label);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
