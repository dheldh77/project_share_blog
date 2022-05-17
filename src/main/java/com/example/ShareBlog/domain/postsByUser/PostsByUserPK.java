package com.example.ShareBlog.domain.postsByUser;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@PrimaryKeyClass
public class PostsByUserPK {

    @PrimaryKeyColumn(name = "user_id", type = PrimaryKeyType.PARTITIONED)
    private UUID userId;

    @PrimaryKeyColumn(name = "date_created", type = PrimaryKeyType.CLUSTERED)
    private LocalDateTime dateCreated;

    @Builder
    public PostsByUserPK(UUID userId, LocalDateTime dateCreated) {
        this.userId = userId;
        this.dateCreated = dateCreated;
    }
}
