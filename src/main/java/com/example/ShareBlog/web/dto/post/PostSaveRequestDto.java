package com.example.ShareBlog.web.dto.post;

import com.example.ShareBlog.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class PostSaveRequestDto {
    private String title;
    private String content;
    private UUID userId;
    private String username;
    private String category;
    private String thumbnailId;

    @Builder
    public PostSaveRequestDto(String title, String content, UUID userId, String username, String category, String thumbnail_id) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.username = username;
        this.category = category;
        this.thumbnailId = thumbnail_id;
    }

    public Post toEntity(){
        return Post.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .username(username)
                .category(category)
                .thumbnailId(thumbnailId)
                .build();
    }
}
