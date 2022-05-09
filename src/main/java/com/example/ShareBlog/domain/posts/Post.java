package com.example.ShareBlog.domain.posts;

import com.example.ShareBlog.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
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

    private String author;

    private String category;

    private String thumbnail_id;

    @Builder
    public Post(String title, String content, String author, String category, String thumbnail_id) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
        this.thumbnail_id = thumbnail_id;
        isNew = true;
    }

    public void update(String title, String content, String author, String category, String thumbnail_id) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
        this.thumbnail_id = thumbnail_id;
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
//    @PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    @CreatedDate
    private LocalDateTime dateCreated;

    @LastModifiedDate
    private LocalDateTime dateModified;

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
