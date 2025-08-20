package com.jlautomate.jlautomate_backend.dto.user;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateUserRequest {
    @NotBlank @Email
    private String email;

    @NotBlank @Size(min = 8)
    private String password;

    @NotBlank @Size(min = 2)
    private String firstName;

    @NotBlank @Size(min = 2)
    private String lastName;

    // Si non fourni, on mettra USER par défaut
    private String[] roles;
}
