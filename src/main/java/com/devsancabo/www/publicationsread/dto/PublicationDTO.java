package com.devsancabo.www.publicationsread.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Data
@Builder
@Validated
public class PublicationDTO {

    private String id;
    @NotNull
    @NotBlank
    @NotEmpty
    private String content;
    private LocalDateTime datetime;
    @NotNull
    private AuthorDTO author;
}
