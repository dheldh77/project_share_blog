package com.example.ShareBlog.web.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class PostUpdateRequestDto {
    private String title;
    private String content;
    private String category;
    private String thumbnailId;

    @Builder
    public PostUpdateRequestDto(String title, String content, String category, String thumbnailId) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.thumbnailId = thumbnailId;
    }
}
