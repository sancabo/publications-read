package com.devsancabo.www.publicationsread.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@Validated
public class AuthorDTO {
    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
    private String username;
}
