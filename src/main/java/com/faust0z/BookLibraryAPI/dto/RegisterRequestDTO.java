package com.faust0z.BookLibraryAPI.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    @Schema(description = "Name of the user", example = "John")
    @NotBlank(message = "Name is required.")
    private String name;

    @Schema(description = "Email of the user", example = "john.doe@example.com")
    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be a valid email address.")
    private String email;

    @Schema(description = "Password of the user", example = "123456")
    @NotBlank(message = "Password is required.")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[A-Z]).*$",
            message = "Password must contain at least one number and one uppercase letter"
    )
    @Size(min = 6, message = "Password must be at least 6 characters long.")
    private String password;
}