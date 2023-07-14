package com.teammatching.demo.web.service;

import com.teammatching.demo.domain.dto.PostDto;
import com.teammatching.demo.domain.dto.PostWithCommentDto;
import com.teammatching.demo.domain.entity.Post;
import com.teammatching.demo.domain.entity.UserAccount;
import com.teammatching.demo.domain.repository.PostRepository;
import com.teammatching.demo.domain.repository.UserAccountRepository;
import com.teammatching.demo.result.exception.NullCheckException;
import com.teammatching.demo.result.exception.NotFoundException;
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
        return PostWithCommentDto.from(findPostById(postId));
    }

    public void createPost(PostDto request) {
        UserAccount userAccount = userAccountRepository.getReferenceById(request.userAccountDto().userId());
        if (request.title() == null) throw new NullCheckException("Post.title");
        if (request.content() == null) throw new NullCheckException("Post.content");
        postRepository.save(request.toEntity(userAccount));
    }

    public void updatePost(Long postId, PostDto request) {
        Post post = findPostById(postId);
        if (post.getUserAccount().getUserId().equals(request.userAccountDto().userId())) {
            if (request.title() != null) post.setTitle(request.title());
            if (request.content() != null) post.setContent(request.content());
            post.setHashtag(request.hashtag());
        }
    }

    public void deletePost(Long postId, String userId) {
        postRepository.deleteByIdAndUserAccount_UserId(postId, userId);
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(NotFoundException.Post::new);
    }
}
