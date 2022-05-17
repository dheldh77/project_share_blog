package com.example.ShareBlog.domain.postsByUser;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Table(value = "posts_by_user")
public class PostsByUser {

    @PrimaryKey
    private PostsByUserPK key;

    private String username;

    @Column("post_id")
    private UUID postId;

    private String title;

    @Column("thumbnail_id")
    private String thumbnailId;

    @Builder
    public PostsByUser(PostsByUserPK key, String username, UUID postId, String title, String thumbnailId) {
        this.key = key;
        this.username = username;
        this.postId = postId;
        this.title = title;
        this.thumbnailId = thumbnailId;
    }

    public void update(String title, String thumbnailId){
        this.title = title;
        this.thumbnailId = thumbnailId;
    }
}
