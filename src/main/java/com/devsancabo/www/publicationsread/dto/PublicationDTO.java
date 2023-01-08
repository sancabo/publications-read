package com.devsancabo.www.publicationsread.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Data
@Builder
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class PublicationDTO {

    private String id;
    @NotNull
    @NotBlank
    @NotEmpty
    private String content;
    private LocalDateTime datetime;
    @NotNull
    private AuthorDTO author;

    @Override
    public String toString(){
        return new StringBuilder()
                .append("[content=").append(this.content)
                .append(", datetime=").append(this.datetime)
                .append(", authorName=").append(this.author.getUsername())
                .append("]").toString();
    }
}
