package com.example.ShareBlog.web.dto;

import com.example.ShareBlog.domain.posts.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostSaveRequestDto {
    private String title;
    private String content;
    private String author;
    private String category;
    private String thumbnailId;

    @Builder
    public PostSaveRequestDto(String title, String content, String author, String category, String thumbnail_id) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
        this.thumbnailId = thumbnail_id;
    }

    public Post toEntity(){
        return Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .category(category)
                .thumbnailId(thumbnailId)
                .build();
    }
}
