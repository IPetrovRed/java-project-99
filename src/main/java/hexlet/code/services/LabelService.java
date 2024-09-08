package hexlet.code.services;

import hexlet.code.dto.labels.LabelCreateDTO;
import hexlet.code.dto.labels.LabelDTO;
import hexlet.code.dto.labels.LabelUpdateDTO;
import hexlet.code.mappers.LabelMapper;
import hexlet.code.repositories.LabelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelService {

    private final LabelRepository repository;
    private final LabelMapper mapper;

    public LabelService(LabelRepository repository, LabelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<LabelDTO> getAll() {
        return mapper.map(repository.findAll());
    }

    public LabelDTO create(LabelCreateDTO labelCreateDTO) {
        var label = mapper.map(labelCreateDTO);
        repository.save(label);
        return mapper.map(label);
    }

    public LabelDTO findById(Long id) {
        return mapper.map(repository.findById(id)
                .orElseThrow());
    }

    public LabelDTO update(LabelUpdateDTO labelUpdateDTO, Long id) {
        var label = repository.findById(id)
                .orElseThrow();
        mapper.update(labelUpdateDTO, label);
        repository.save(label);
        return mapper.map(label);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
