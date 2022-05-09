package com.example.ShareBlog.config;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.EnableCassandraAuditing;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
@EnableCassandraRepositories
@EnableCassandraAuditing
public class CassandraConfig extends CassandraAutoConfiguration {

    @Bean
    AuditorAware<String> auditorAware(){
        // TODO: check if configuration is correct
        return () -> Optional.of("user");
    }

}
