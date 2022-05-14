package com.example.ShareBlog.domain.posts;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostRepository extends CassandraRepository<Post, UUID> {

}
