package com.faust0z.BookLibraryAPI.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserPasswordDTO {
    @Schema(description = "Current user password", example = "Pass123")
    private String currentPassword;

    @Schema(description = "New user password", example = "NewPass123")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[A-Z]).*$",
            message = "Password must contain at least one number and one uppercase letter"
    )
    @Size(min = 6, message = "Password must be at least 6 characters long.")
    private String newPassword;
}
