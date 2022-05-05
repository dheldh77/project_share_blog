package com.example.ShareBlog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.EnableCassandraAuditing;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
@EnableCassandraAuditing
public class CassandraAuditing {

    @Bean
    AuditorAware<String> auditorAware(){
        // TODO: check if configuration is correct
        return () -> Optional.of("user");
    }


}
