package com.example.ShareBlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

//@EnableAutoConfiguration
@SpringBootApplication
public class ShareBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShareBlogApplication.class, args);
	}

}
