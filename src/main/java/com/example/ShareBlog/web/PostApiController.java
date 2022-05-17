package com.example.ShareBlog.web;

import com.example.ShareBlog.domain.postsByUser.PostsByUserPK;
import com.example.ShareBlog.service.post.PostService;
import com.example.ShareBlog.service.postsByUser.PostsByUserService;
import com.example.ShareBlog.web.dto.post.PostResponseDto;
import com.example.ShareBlog.web.dto.post.PostSaveRequestDto;
import com.example.ShareBlog.web.dto.post.PostUpdateRequestDto;
import com.example.ShareBlog.web.dto.postsByUser.PostsByUserSaveRequestDto;
import com.example.ShareBlog.web.dto.postsByUser.PostsByUserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;
    private final PostsByUserService postsByUserService;

    @PostMapping("/api/v1/post")
    public UUID save(@RequestBody PostSaveRequestDto requestDto){
        UUID postId = postService.save(requestDto);

        // To get dateCreated
        PostResponseDto post = postService.findById(postId);

        // Save to PostsByUser
        PostsByUserSaveRequestDto postsByUserRequestDto = PostsByUserSaveRequestDto.builder()
                .dateCreated(post.getDateCreated())
                .postId(postId)
                .userId(requestDto.getUserId())
                .username(requestDto.getUsername())
                .thumbnailId(requestDto.getThumbnailId())
                .title(requestDto.getTitle())
                .build();
        postsByUserService.save(postsByUserRequestDto);

        return postId;
    }

    @PutMapping("/api/v1/post/{id}")
    public UUID update(@PathVariable UUID id, @RequestBody PostUpdateRequestDto requestDto){
        UUID postId = postService.update(id, requestDto);

        PostResponseDto post = postService.findById(id);
        PostsByUserPK key = PostsByUserPK.builder()
                .userId(post.getUserId())
                .dateCreated(post.getDateCreated())
                .build();

        // Update PostsByUser
        PostsByUserUpdateRequestDto postsByUserUpdateRequestDto =
                PostsByUserUpdateRequestDto.builder()
                        .title(requestDto.getTitle())
                        .thumbnailId(requestDto.getThumbnailId())
                        .build();
        postsByUserService.update(key, postsByUserUpdateRequestDto);

        return postId;
    }

    @GetMapping("/api/v1/post/{id}")
    public PostResponseDto findById(@PathVariable UUID id){
        return postService.findById(id);
    }

    @DeleteMapping("/api/v1/post/{id}")
    public void delete(@PathVariable UUID id){

        PostResponseDto postResponseDto = postService.findById(id);
        postService.delete(id);

        // delete using key (userid and datecreated)? Or query by postId?
        PostsByUserPK key = PostsByUserPK.builder()
                .userId(postResponseDto.getUserId())
                .dateCreated(postResponseDto.getDateCreated())
                .build();
        postsByUserService.delete(key);
    }


}
