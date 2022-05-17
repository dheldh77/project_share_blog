package com.example.ShareBlog.web.dto.postsByUser;

import com.example.ShareBlog.domain.postsByUser.PostsByUser;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class PostsByUserResponseDto {
    private UUID userId;
    private LocalDateTime dateCreated;
    private String username;
    private UUID postId;
    private String title;
    private String thumbnailId;

    public PostsByUserResponseDto(PostsByUser entity) {
        this.userId = entity.getKey().getUserId();
        this.dateCreated = entity.getKey().getDateCreated();
        this.username = entity.getUsername();
        this.postId = entity.getPostId();
        this.title = entity.getTitle();
        this.thumbnailId = entity.getThumbnailId();
    }
}
