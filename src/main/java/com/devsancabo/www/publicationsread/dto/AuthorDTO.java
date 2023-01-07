package com.devsancabo.www.publicationsread.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class AuthorDTO {
    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
    private String username;

    public static AuthorDTO newAuthor() {
        return new AuthorDTO();
    }

    public AuthorDTO withUsername(String username) {
        this.username = username;
        return this;
    }

    public AuthorDTO withId(Long id) {
        this.id = id;
        return this;
    }
}
