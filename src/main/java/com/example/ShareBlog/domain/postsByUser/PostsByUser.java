package com.example.ShareBlog.domain.postsByUser;

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
    private PostsByUserPrimaryKey key;

    private String username;

    @Column("post_id")
    private UUID postId;

    private String title;

    private String thumbnailId;


}
