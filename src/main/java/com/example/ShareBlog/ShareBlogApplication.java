package com.example.ShareBlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@SpringBootApplication
@EnableCassandraRepositories
public class ShareBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShareBlogApplication.class, args);
	}

}
