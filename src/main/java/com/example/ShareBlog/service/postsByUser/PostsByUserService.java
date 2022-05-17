package com.example.ShareBlog.service.postsByUser;

import com.example.ShareBlog.domain.postsByUser.PostsByUser;
import com.example.ShareBlog.domain.postsByUser.PostsByUserPK;
import com.example.ShareBlog.domain.postsByUser.PostsByUserRepository;
import com.example.ShareBlog.web.dto.postsByUser.PostsByUserSaveRequestDto;
import com.example.ShareBlog.web.dto.postsByUser.PostsByUserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PostsByUserService {

    private final PostsByUserRepository postsByUserRepository;

    @Transactional
    public PostsByUserPK save(PostsByUserSaveRequestDto requestDto) {
         return postsByUserRepository.save(requestDto.toEntity()).getKey();
    }

    @Transactional
    public PostsByUserPK update(PostsByUserPK key, PostsByUserUpdateRequestDto requestDto) {

        PostsByUser post = postsByUserRepository.findById(key).orElseThrow(() ->
                new IllegalArgumentException("The post with userId: " + key.getUserId() +
                        " and createdDate: " + key.getDateCreated() + " does not exist."));

        post.update(requestDto.getTitle(), requestDto.getThumbnailId());
        postsByUserRepository.save(post);
        return post.getKey();
    }

    @Transactional
    public void delete(PostsByUserPK key) {
        PostsByUser post = postsByUserRepository.findById(key).orElseThrow(() ->
                new IllegalArgumentException("The post with userId: " + key.getUserId() +
                        " and createdDate: " + key.getDateCreated() + " does not exist."));

        postsByUserRepository.delete(post);
    }

    @Transactional
    public List<PostsByUser> findAllByUser(UUID userId) {

        return postsByUserRepository.findByKeyUserId(userId);
    }

}
