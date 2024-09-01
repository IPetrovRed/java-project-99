package hexlet.code.mappers;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public abstract class JsonNullable {

    public <T> org.openapitools.jackson.nullable.JsonNullable<T> wrap(T entity) {
        return org.openapitools.jackson.nullable.JsonNullable.of(entity);
    }

    public <T> T unwrap(org.openapitools.jackson.nullable.JsonNullable<T> jsonNullable) {
        return jsonNullable == null ? null : jsonNullable.orElse(null);
    }

    @Condition
    public <T> boolean isPresent(org.openapitools.jackson.nullable.JsonNullable<T> nullable) {
        return nullable != null && nullable.isPresent();
    }
}
