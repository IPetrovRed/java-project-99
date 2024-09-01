package hexlet.code.mappers;

import hexlet.code.dto.labels.LabelCreate;
import hexlet.code.dto.labels.LabelDTO;
import hexlet.code.dto.labels.LabelUpdate;
import hexlet.code.model.Label;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(
        uses = {JsonNullable.class, Reference.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class LabelMapper {
    public abstract Label map(LabelCreate dto);
    public abstract List<LabelDTO> map(List<Label> list);

    public abstract LabelDTO map(Label model);

    public abstract void update(LabelUpdate dto, @MappingTarget Label model);

}
