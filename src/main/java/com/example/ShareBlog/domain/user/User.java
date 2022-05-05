package com.example.ShareBlog.domain.user;

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

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Table(value = "user_by_id")
public class User implements Persistable<UUID> {
    @Id
    @PrimaryKeyColumn(name = "post_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID id;

    @PrimaryKeyColumn(ordinal = 1, ordering = Ordering.ASCENDING)
    private String lastName;

    @PrimaryKeyColumn(ordinal = 2, ordering = Ordering.ASCENDING)
    private String firstName;

    private String username;

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
