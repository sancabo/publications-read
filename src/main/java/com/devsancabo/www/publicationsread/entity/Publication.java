package com.devsancabo.www.publicationsread.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "publications")
public class Publication {
    @Id
    public String id;
    private Long authorId;
    private String authorName;
    private LocalDateTime dateTime;
    private String content;

    public Publication(Long authorId, String authorName, LocalDateTime dateTime, String content) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.dateTime = dateTime;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getContent() {
        return content;
    }
}
