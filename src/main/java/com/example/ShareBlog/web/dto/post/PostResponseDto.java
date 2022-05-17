package com.example.ShareBlog.web.dto.post;

import com.example.ShareBlog.domain.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@NoArgsConstructor
public class PostResponseDto {
    private String title;
    private String content;
    private UUID userId;
    private String username;
    private String category;
    private String thumbnailId;
    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;

    public PostResponseDto(Post entity){
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.userId = entity.getUserId();
        this.username = entity.getUsername();
        this.category = entity.getCategory();
        this.thumbnailId = entity.getThumbnailId();
        this.dateCreated = entity.getDateCreated();
        this.dateModified = entity.getDateModified();

    }
}
