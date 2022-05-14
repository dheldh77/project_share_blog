package com.example.ShareBlog.domain.category;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CassandraRepository<Category, CategoryPrimaryKey> {

}
