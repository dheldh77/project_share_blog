package com.example.ShareBlog.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class BaseTimeEntity implements Persistable<UUID> {
    // TODO: move common properties like timestamp to a base class (ex. BaseTimeEntity)

    @CreatedDate
    private LocalDateTime dateCreated;

    @LastModifiedDate
    private LocalDateTime dateModified;

    @Transient
    private boolean isNew;

    public BaseTimeEntity() {
        this.isNew = true;
    }

    @Override
    public UUID getId() {
        return null;
    }

    @Override
    public boolean isNew() {
        if(isNew) {
            isNew = false;
            return true;
        }
        return isNew;
    }
}
