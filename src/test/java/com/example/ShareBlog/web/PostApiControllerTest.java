package com.example.ShareBlog.web;

import com.datastax.driver.core.Session;
import com.example.ShareBlog.domain.post.Post;
import com.example.ShareBlog.domain.post.PostRepository;
import com.example.ShareBlog.domain.postsByUser.PostsByUser;
import com.example.ShareBlog.domain.postsByUser.PostsByUserPK;
import com.example.ShareBlog.domain.postsByUser.PostsByUserRepository;
import com.example.ShareBlog.web.dto.post.PostResponseDto;
import com.example.ShareBlog.web.dto.post.PostSaveRequestDto;
import com.example.ShareBlog.web.dto.post.PostUpdateRequestDto;
import com.example.ShareBlog.web.dto.postsByUser.PostsByUserResponseDto;
import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.Optional;
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
    public void reset(){
        postRepository.deleteAll();
        postsByUserRepository.deleteAll();
    }

    @Test
    public void publishPost() throws Exception {
        String title = "title";
        String content = "content";
        UUID userId = UUID.randomUUID();
        String username = "author";
        String category = "category";
        String thumbnailId = "thumbnail";
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .username(username)
                .category(category)
                .thumbnail_id(thumbnailId)
                .build();

        String url = "http://localhost:" + port + "/api/v1/post";

        ResponseEntity<UUID> responseEntity = testRestTemplate.postForEntity(url, requestDto, UUID.class);

        List<Post> posts = postRepository.findAll();
        Post post = posts.get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getUsername()).isEqualTo(username);
        assertThat(post.getCategory()).isEqualTo(category);
        assertThat(post.getThumbnailId()).isEqualTo(thumbnailId);

        // Check PostsByUser
        List<PostsByUser> postsByUserList = postsByUserRepository.findAll();
        PostsByUser postsByUser = postsByUserList.get(0);
        assertThat(postsByUser.getPostId()).isEqualTo(post.getId());
        assertThat(postsByUser.getUsername()).isEqualTo(username);
        assertThat(postsByUser.getThumbnailId()).isEqualTo(thumbnailId);
        assertThat(postsByUser.getTitle()).isEqualTo(title);
    }

    @Test
    public void editPost() {
        String title = "title";
        String content = "content";
        UUID userId = UUID.randomUUID();
        String username = "username";
        String category = "category";
        String thumbnailId = "thumbnail";

        Post post = postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .username(username)
                .category(category)
                .thumbnailId(thumbnailId)
                .build());

        // Must save to PostsByUser too
        PostsByUserPK key = PostsByUserPK.builder()
                .userId(post.getUserId())
                .dateCreated(post.getDateCreated())
                .build();
        PostsByUser postsByUser = postsByUserRepository.save(PostsByUser.builder()
                .title(title)
                .key(key)
                .thumbnailId(thumbnailId)
                .username(username)
                .postId(post.getId())
                .build());

        UUID updateId = post.getId();

        String title2 = "title2";
        String content2 = "content2";

        PostUpdateRequestDto requestDto = PostUpdateRequestDto.builder()
                .title(title2)
                .content(content2)
                .category(category)
                .thumbnailId(thumbnailId)
                .build();

        String url = "http://localhost:" + port + "/api/v1/post/" + updateId;

        HttpEntity<PostUpdateRequestDto> httpEntity = new HttpEntity<>(requestDto);
        ResponseEntity<UUID> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, httpEntity, UUID.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Post> posts = postRepository.findAll();
        Post updatedPost = posts.get(0);
        assertThat(updatedPost.getTitle()).isEqualTo(title2);
        assertThat(updatedPost.getContent()).isEqualTo(content2);

        // Check PostsByUser
        key = PostsByUserPK.builder()
                .userId(updatedPost.getUserId()).
                dateCreated(updatedPost.getDateCreated())
                .build();
        Optional<PostsByUser> postsBU = postsByUserRepository.findById(key);
        PostsByUser updatedPostsByUser = postsBU.get();
        assertThat(updatedPostsByUser.getTitle()).isEqualTo(title2);
    }

    @Test
    public void getPost() {
        String title = "title";
        String content = "content";
        UUID userId = UUID.randomUUID();
        String username = "username";
        String category = "category";
        String thumbnailId = "thumbnail";
        Post post = postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .username(username)
                .category(category)
                .thumbnailId(thumbnailId)
                .build());

        UUID getId = post.getId();

        String url = "http://localhost:" + port + "/api/v1/post/" + getId;

        ResponseEntity<PostResponseDto> responseEntity = testRestTemplate.getForEntity(url, PostResponseDto.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(responseEntity.getBody().getTitle()).isEqualTo(title);
        assertThat(responseEntity.getBody().getContent()).isEqualTo(content);
    }

    @Test
    public void deletePost() {
        String title = "title";
        String content = "content";
        UUID userId = UUID.randomUUID();
        String username = "username";
        String category = "category";
        String thumbnailId = "thumbnail";

        Post post = postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .username(username)
                .category(category)
                .thumbnailId(thumbnailId)
                .build());

        // Must save to PostsByUser too
        PostsByUserPK key = PostsByUserPK.builder()
                .userId(post.getUserId())
                .dateCreated(post.getDateCreated())
                .build();
        PostsByUser postsByUser = postsByUserRepository.save(PostsByUser.builder()
                .title(title)
                .key(key)
                .thumbnailId(thumbnailId)
                .username(username)
                .postId(post.getId())
                .build());

        UUID getId = post.getId();

        String url = "http://localhost:" + port + "/api/v1/post/" + getId;

        testRestTemplate.delete(url);

        List<Post> posts = postRepository.findAll();
        assertThat(posts.stream().count()).isEqualTo(0);

        List<PostsByUser> postsByUserList = postsByUserRepository.findAll();
        assertThat(postsByUserList.stream().count()).isEqualTo(0);
    }
}
