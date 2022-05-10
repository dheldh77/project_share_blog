package com.example.ShareBlog.domain.posts;

import com.datastax.driver.core.Session;
import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

// NOTE: Docker must be installed and running for this test
@Testcontainers
@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

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

    @After
    public void tearDown() throws Exception {
        postRepository.deleteAll();
    }

    @Test
    public void saveAndGetPost() {
        String title = "title";
        String content = "content";
        String author = "author";
        String category = "category";

        postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .category(category)
                .build());

        List<Post> posts = postRepository.findAll();
        Post post = posts.get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getAuthor()).isEqualTo(author);
        assertThat(post.getCategory()).isEqualTo(category);
        System.out.println(post.getDateCreated() + ", " + post.getDateModified());
    }

    @Test
    public void TestDateModified() throws InterruptedException {
        UUID post_id = UUID.randomUUID();
        String title = "title";
        String content = "content";
        String author = "author";
        String category = "category";
        String thumbnailid = "thumbnail";

        postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .category(category)
                .thumbnailId(thumbnailid)
                .build());

        String title2 = "title2";
        String content2 = "content2";
        String author2 = "author2";
        String thumbnailid2 = "thumbnail2";
        String category2 = "category2";

        // Get post
        List<Post> posts = postRepository.findAll();
        Post post = posts.get(0);
        UUID updateId = post.getId();

        LocalDateTime created1 = post.getDateCreated();
        LocalDateTime modified1 = post.getDateModified();
        System.out.println(created1 + ", " + modified1);

        // Update post
        post.update(title2, content2, author2, category2, thumbnailid2);
        postRepository.save(post);

        // Get updated post by id
        Optional<Post> updatedPost = postRepository.findById(updateId);

        System.out.println(post.getDateCreated() + ", " + post.getDateModified());

        assertThat(updatedPost.get().getTitle()).isEqualTo(title2);
        assertThat(updatedPost.get().getContent()).isEqualTo(content2);
        assertThat(updatedPost.get().getAuthor()).isEqualTo(author2);
        assertThat(updatedPost.get().getCategory()).isEqualTo(category2);
        assertThat(updatedPost.get().getThumbnailId()).isEqualTo(thumbnailid2);
        assertThat(modified1).isNotEqualTo(post.getDateModified());
    }


}
