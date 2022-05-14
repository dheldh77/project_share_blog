package com.example.ShareBlog.domain.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@PrimaryKeyClass
public class CategoryPrimaryKey {

    @PrimaryKeyColumn(name = "category_id", type = PrimaryKeyType.PARTITIONED)
    private UUID id;

    @PrimaryKeyColumn(name = "category", type = PrimaryKeyType.CLUSTERED)
    private String category;


}
