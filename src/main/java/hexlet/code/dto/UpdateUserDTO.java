package hexlet.code.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class UpdateUserDTO {

    @NotNull
    private JsonNullable<String> email;

    private JsonNullable<String> firstName;

    private JsonNullable<String> lastName;

    @NotNull
    private JsonNullable<String> password;

    private JsonNullable<String> encryptedPassword;
}
