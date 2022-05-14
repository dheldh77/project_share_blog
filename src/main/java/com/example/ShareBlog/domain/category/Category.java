package com.example.ShareBlog.domain.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;


@Getter
@NoArgsConstructor
@Table(value = "category_by_id")
public class Category {

    @PrimaryKey
    private CategoryPrimaryKey key;

    // TODO: timestamp?
}
