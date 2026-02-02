package com.faust0z.BookLibraryAPI.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @Schema(description = "Email of the user", example = "john.doe@example.com")
    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be a valid email address.")
    private String email;

    @Schema(description = "Password of the user", example = "123456")
    @NotBlank(message = "Password is required.")
    private String password;
}