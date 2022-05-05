package com.example.ShareBlog.web;

import com.example.ShareBlog.service.post.PostService;
import com.example.ShareBlog.web.dto.PostResponseDto;
import com.example.ShareBlog.web.dto.PostSaveRequestDto;
import com.example.ShareBlog.web.dto.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.procedure.internal.PostgresCallableStatementSupport;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping("/api/v1/post")
    public UUID save(@RequestBody PostSaveRequestDto requestDto){
        return postService.save(requestDto);
    }

    @PutMapping("/api/v1/post/{id}")
    public UUID update(@PathVariable UUID id, @RequestBody PostUpdateRequestDto requestDto){
        return postService.update(id, requestDto);
    }

    @GetMapping("/api/v1/post/{id}")
    public PostResponseDto findById(@PathVariable UUID id){
        return postService.findById(id);
    }

    @DeleteMapping("/api/v1/post/{id}")
    public void delete(@PathVariable UUID id){
        postService.delete(id);
    }

}
