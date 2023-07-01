package com.teammatching.demo.web.service;

import com.teammatching.demo.domain.dto.PostDto;
import com.teammatching.demo.domain.dto.PostWithCommentDto;
import com.teammatching.demo.domain.entity.Post;
import com.teammatching.demo.domain.entity.UserAccount;
import com.teammatching.demo.domain.repository.PostRepository;
import com.teammatching.demo.domain.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserAccountRepository userAccountRepository;

    @Transactional(readOnly = true)
    public Page<PostDto> getSimplePosts(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostDto::from);
    }

    @Transactional(readOnly = true)
    public PostWithCommentDto getPostById(Long postId) {
        return postRepository.findById(postId)
                .map(PostWithCommentDto::from)
                .orElseThrow(RuntimeException::new);        //TODO: 예외 처리 구현 필요
    }

    public void createPost(PostDto request) {
        UserAccount userAccount = userAccountRepository.getReferenceById(request.userAccountDto().userId());
        postRepository.save(request.toEntity(userAccount));
    }

    public void updatePost(Long postId, PostDto request) {
        UserAccount userAccount = userAccountRepository.getReferenceById(request.userAccountDto().userId());
        Post post = postRepository.findById(postId).orElseThrow(RuntimeException::new); //TODO: 예외 처리 구현 필요
        if (post.getUserAccount().equals(userAccount)) {
            if (request.title() != null) {
                post.setTitle(request.title());
            }
            if (request.content() != null) {
                post.setContent(request.content());
            }
            post.setHashtag(request.hashtag());
        }
    }

    public void deletePost(Long postId, String userId) {
        postRepository.deleteByIdAndUserAccount_UserId(postId, userId);
    }
}
