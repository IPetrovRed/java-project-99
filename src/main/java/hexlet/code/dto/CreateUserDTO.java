package hexlet.code.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDTO {

    @NotNull
    @Email
    private String email;

    private String firstName;

    private String lastName;

    @NotNull
    @Min(3)
    private String password;

    private String encryptedPassword;
}
