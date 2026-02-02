package com.faust0z.BookLibraryAPI.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserDTO {
    @Schema(description = "Name of the user", example = "John")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;
}


