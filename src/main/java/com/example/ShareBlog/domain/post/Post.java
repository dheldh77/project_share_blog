package com.example.ShareBlog.domain.post;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Table(value = "post_by_id")    // name of the table
public class Post implements Persistable<UUID> {

    @Id
    @PrimaryKeyColumn(name = "post_id", type = PrimaryKeyType.PARTITIONED)
    private UUID id;

    private String title;

    private String content;

    @Column("user_id")
    private UUID userId;

    private String username;

    private String category;

    @Column("thumbnail_id")
    private String thumbnailId;

    @Builder
    public Post(String title, String content, UUID userId, String username, String category, String thumbnailId) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.username = username;
        this.category = category;
        this.thumbnailId = thumbnailId;
        isNew = true;
    }

    public void update(String title, String content, String category, String thumbnailId) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.thumbnailId = thumbnailId;
    }

    // TODO: Individual update functions required?
    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateCategory(String category) {
        this.category = category;
    }

    // TODO: move common properties like timestamp to a base class (ex. BaseTimeEntity)
    @CreatedDate
    private LocalDateTime dateCreated;

    @LastModifiedDate
    private LocalDateTime dateModified;

    @Getter(AccessLevel.NONE)
    @Transient
    private boolean isNew;

    @Override
    public boolean isNew() {
        if (isNew) {
            isNew = false;
            return true;
        }
        return false;
    }

}
