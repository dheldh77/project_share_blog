package com.example.ShareBlog.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateRequestDto {
    private String title;
    private String content;
    private String author;
    private String category;
    private String thumbnail_id;

    @Builder
    public PostUpdateRequestDto(String title, String content, String author, String category, String thumbnail_id) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
        this.thumbnail_id = thumbnail_id;
    }
}
