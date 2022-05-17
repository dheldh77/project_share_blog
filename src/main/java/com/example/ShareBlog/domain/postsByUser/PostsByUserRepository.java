package com.example.ShareBlog.domain.postsByUser;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostsByUserRepository extends CassandraRepository<PostsByUser, PostsByUserPK> {

    // syntax: findBy{primary_key_property_name}{property_name}
    List<PostsByUser> findByKeyUserId(final UUID userId);

}
