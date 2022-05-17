package com.example.ShareBlog.domain.postsByUser;

import com.datastax.driver.core.Session;
import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
public class PostsByUserRepositoryTest {
    @Autowired
    private PostsByUserRepository postsByUserRepository;

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

    @BeforeEach
    public void reset() throws Exception {
        postsByUserRepository.deleteAll();
    }

    @Test
    public void saveAndGetPost() {
        UUID userId = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        String title = "title";
        String username = "username";
        String thumbnailId = "thumbnail";

        // Create primary key
        PostsByUserPK key = PostsByUserPK.builder()
                .userId(userId)
                .dateCreated(time)
                .build();

        postsByUserRepository.save(PostsByUser.builder()
                .key(key)
                .title(title)
                .username(username)
                .thumbnailId(thumbnailId)
                .build());

        List<PostsByUser> posts = postsByUserRepository.findAll();
        PostsByUser post = posts.get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getUsername()).isEqualTo(username);
        assertThat(post.getThumbnailId()).isEqualTo(thumbnailId);

        assertThat(post.getKey().getUserId()).isEqualTo(userId);
        assertThat(post.getKey().getDateCreated()).isEqualTo(time);
    }

    @Test
    public void getPostsByUser() throws InterruptedException {
        LocalDateTime time1 = LocalDateTime.now();
        String title1 = "title";
        String thumbnailId1 = "thumbnail";

        UUID userId = UUID.randomUUID();
        String username = "username";

        // Create primary key
        PostsByUserPK key1 = PostsByUserPK.builder()
                .userId(userId)
                .dateCreated(time1)
                .build();

        Thread.sleep(1000);

        LocalDateTime time2 = LocalDateTime.now();
        String title2 = "title";
        String thumbnailId2 = "thumbnail";

        // Create primary key
        PostsByUserPK key2 = PostsByUserPK.builder()
                .userId(userId)
                .dateCreated(time2)
                .build();

        // Save post1 first
        postsByUserRepository.save(PostsByUser.builder()
                .key(key1)
                .title(title1)
                .username(username)
                .thumbnailId(thumbnailId1)
                .build());
        postsByUserRepository.save(PostsByUser.builder()
                .key(key2)
                .title(title2)
                .username(username)
                .thumbnailId(thumbnailId2)
                .build());

        // Should be ordered by dateCreated in descending order, most recently published
        // (post2 should come 1st)
        List<PostsByUser> postList = postsByUserRepository.findByKeyUserId(userId);

        System.out.println(postList.get(0).getTitle());

        PostsByUser post2 = postList.get(0);
        PostsByUser post1 = postList.get(1);

        assertThat(post1.getTitle()).isEqualTo(title1);
        assertThat(post2.getTitle()).isEqualTo(title2);

        System.out.println("Post list size: " + postList.size());
        System.out.println("post2: " + post2.getTitle() + "-"
                + post2.getKey().getDateCreated());
        System.out.println("post1: " + post1.getTitle() + "-"
                + post1.getKey().getDateCreated());
    }

}
