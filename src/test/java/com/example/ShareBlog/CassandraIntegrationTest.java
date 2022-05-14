package com.example.ShareBlog;

import com.datastax.driver.core.Session;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

// NOTE: Docker must be installed and running for this test
@Testcontainers
@SpringBootTest
public class CassandraIntegrationTest {

    private static final String KEYSPACE_NAME = "TestKeyspace";

    @Container
    private static final CassandraContainer cassandra =
            (CassandraContainer) new CassandraContainer("cassandra:4.0.3")
                    .withExposedPorts(9042);

    @BeforeAll
    static void setupCassandra() {
        System.setProperty("spring.data.cassandra.keyspace-name", KEYSPACE_NAME);
        System.setProperty("spring.data.cassandra.contact-points", cassandra.getHost());
        System.setProperty("spring.data.cassandra.port",
                String.valueOf(cassandra.getMappedPort(9042)));

        try (Session session = cassandra.getCluster().connect()) {
            session.execute("CREATE KEYSPACE IF NOT EXISTS " + KEYSPACE_NAME +
                    " WITH replication = \n" +
                    "{'class':'SimpleStrategy','replication_factor':'1'};");
        }
    }

    @Test
    void cassandraIsRunning() {
        assertThat(cassandra.isRunning()).isTrue();
    }

}
