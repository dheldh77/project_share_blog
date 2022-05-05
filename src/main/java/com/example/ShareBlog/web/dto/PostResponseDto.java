package com.example.ShareBlog.web.dto;

import com.example.ShareBlog.domain.posts.Post;
import lombok.Getter;


@Getter
public class PostResponseDto {
    private String title;
    private String content;
    private String author;
    private String category;
    private String thumbnail_id;

    public PostResponseDto(Post entity){
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.category = entity.getCategory();
        this.thumbnail_id = entity.getThumbnail_id();
    }
}
