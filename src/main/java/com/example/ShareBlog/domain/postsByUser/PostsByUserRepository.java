package com.example.ShareBlog.domain.postsByUser;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostsByUserRepository extends CassandraRepository<PostsByUser, PostsByUserPrimaryKey> {

//    Optional<PostsByUser> findAllById(PostsByUserPrimaryKey key);

}
