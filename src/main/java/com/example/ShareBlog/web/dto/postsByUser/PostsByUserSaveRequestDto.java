package com.example.ShareBlog.web.dto.postsByUser;

import com.example.ShareBlog.domain.postsByUser.PostsByUser;
import com.example.ShareBlog.domain.postsByUser.PostsByUserPK;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class PostsByUserSaveRequestDto {

    private UUID userId;
    private LocalDateTime dateCreated;
    private String username;
    private UUID postId;
    private String title;
    private String thumbnailId;

    @Builder
    public PostsByUserSaveRequestDto(UUID userId, LocalDateTime dateCreated, String username, UUID postId, String title, String thumbnailId) {
        this.userId = userId;
        this.dateCreated = dateCreated;
        this.username = username;
        this.postId = postId;
        this.title = title;
        this.thumbnailId = thumbnailId;
    }

    public PostsByUser toEntity() {
        PostsByUserPK key = PostsByUserPK.builder()
                .userId(userId)
                .dateCreated(dateCreated)
                .build();

        return PostsByUser.builder()
                .key(key)
                .username(username)
                .postId(postId)
                .title(title)
                .thumbnailId(thumbnailId)
                .build();
    }
}
