package com.example.ShareBlog.service.post;

import com.example.ShareBlog.domain.posts.Post;
import com.example.ShareBlog.domain.posts.PostRepository;
import com.example.ShareBlog.web.dto.PostResponseDto;
import com.example.ShareBlog.web.dto.PostSaveRequestDto;
import com.example.ShareBlog.web.dto.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public UUID save(PostSaveRequestDto requestDto){
        return postRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public UUID update(UUID id, PostUpdateRequestDto requestDto){
        Post post = postRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("The post with the id: " + id + " does not exist."));

        post.update(requestDto.getTitle(),
                requestDto.getContent(),
                requestDto.getAuthor(),
                requestDto.getCategory(),
                requestDto.getThumbnail_id());
        postRepository.save(post);

        return id;
    }

    @Transactional
    public PostResponseDto findById(UUID id){
        Post post = postRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("The post with the id: " + id + " does not exist."));

        return new PostResponseDto(post);
    }

    @Transactional
    public void delete(UUID id){
        Post post = postRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("The post with the id: " + id + " does not exist."));

        postRepository.delete(post);
    }
}
