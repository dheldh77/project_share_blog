package com.example.ShareBlog.web;

import com.datastax.driver.core.Session;
import com.example.ShareBlog.domain.posts.Post;
import com.example.ShareBlog.domain.posts.PostRepository;
import com.example.ShareBlog.web.dto.PostSaveRequestDto;
import com.example.ShareBlog.web.dto.PostUpdateRequestDto;
import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

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
    public void publishPost() throws Exception {
        String title = "title";
        String content = "content";
        String author = "author";
        String category = "category";
        String thumbnailId = "thumbnail";
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .category(category)
                .thumbnail_id(thumbnailId)
                .build();

        String url = "http://localhost:" + port + "/api/v1/post";

        ResponseEntity<UUID> responseEntity = testRestTemplate.postForEntity(url, requestDto, UUID.class);

        List<Post> posts = postRepository.findAll();
        Post post = posts.get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getAuthor()).isEqualTo(author);
        assertThat(post.getCategory()).isEqualTo(category);
        assertThat(post.getThumbnail_id()).isEqualTo(thumbnailId);
    }

    @Test
    public void editPost() {
        String title = "title";
        String content = "content";
        String author = "author";
        String category = "category";
        String thumbnailId = "thumbnail";
        Post post = postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .category(category)
                .thumbnail_id(thumbnailId)
                .build());

        UUID updateId = post.getId();

        String title2 = "title2";
        String content2 = "content2";

        PostUpdateRequestDto requestDto = PostUpdateRequestDto.builder()
                .title(title2)
                .content(content2)
                .author(author)
                .category(category)
                .thumbnail_id(thumbnailId)
                .build();

        String url = "http://localhost:" + port + "/api/v1/post/" + updateId;

        HttpEntity<PostUpdateRequestDto> httpEntity = new HttpEntity<>(requestDto);
        ResponseEntity<UUID> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, httpEntity, UUID.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Post> posts = postRepository.findAll();
        Post updatedPost = posts.get(0);
        assertThat(updatedPost.getTitle()).isEqualTo(title2);
        assertThat(updatedPost.getContent()).isEqualTo(content2);
    }

    @Test
    public void getPost(){
        String title = "title";
        String content = "content";
        String author = "author";
        String category = "category";
        String thumbnailId = "thumbnail";
        Post post = postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .category(category)
                .thumbnail_id(thumbnailId)
                .build());

        UUID getId = post.getId();

        String url = "http://localhost:" + port + "/api/v1/post/" + getId;

        // TODO: fix get post test (cannot deserialize PostResponseDto)
//        ResponseEntity<PostResponseDto> responseEntity = testRestTemplate.getForEntity(url, PostResponseDto.class);
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
////
//        assertThat(responseEntity.getBody().getTitle()).isEqualTo(title);
//        assertThat(responseEntity.getBody().getContent()).isEqualTo(content);
    }

    @Test
    public void deletePost(){
        String title = "title";
        String content = "content";
        String author = "author";
        String category = "category";
        String thumbnailId = "thumbnail";
        Post post = postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .category(category)
                .thumbnail_id(thumbnailId)
                .build());

        UUID getId = post.getId();

        String url = "http://localhost:" + port + "/api/v1/post/" + getId;

        testRestTemplate.delete(url);

        List<Post> posts = postRepository.findAll();
        assertThat(posts.stream().count()).isEqualTo(0);
    }
}
