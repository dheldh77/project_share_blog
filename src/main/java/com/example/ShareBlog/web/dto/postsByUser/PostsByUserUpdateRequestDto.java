package com.example.ShareBlog.web.dto.postsByUser;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class PostsByUserUpdateRequestDto {

    private String title;
    private String thumbnailId;

    @Builder
    public PostsByUserUpdateRequestDto(String title, String thumbnailId) {
        this.title = title;
        this.thumbnailId = thumbnailId;
    }
}
