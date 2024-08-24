package hexlet.code.mapper;

import hexlet.code.dto.CreateUserDTO;
import hexlet.code.dto.UserDTO;
import hexlet.code.dto.UpdateUserDTO;
import hexlet.code.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class UserMapper {

    public abstract User map(CreateUserDTO userCreateDTO);

    public abstract UserDTO map(User user);

    public abstract void update(UpdateUserDTO userUpdateDTO, @MappingTarget User user);

}
