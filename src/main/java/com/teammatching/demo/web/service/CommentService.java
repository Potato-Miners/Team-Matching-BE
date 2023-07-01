package com.teammatching.demo.web.service;

import com.teammatching.demo.domain.dto.CommentDto;
import com.teammatching.demo.domain.dto.UserAccountDto;
import com.teammatching.demo.domain.entity.Comment;
import com.teammatching.demo.domain.entity.Post;
import com.teammatching.demo.domain.entity.UserAccount;
import com.teammatching.demo.domain.repository.CommentRepository;
import com.teammatching.demo.domain.repository.PostRepository;
import com.teammatching.demo.domain.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserAccountRepository userAccountRepository;

    public void createComment(CommentDto request) {
        UserAccount userAccount = userAccountRepository.getReferenceById(request.userAccountDto().userId());
        Post post = postRepository.getReferenceById(request.postId());
        commentRepository.save(request.toEntity(post, userAccount));
    }

    public void updateComment(Long commentId, CommentDto request) {
        UserAccount userAccount = userAccountRepository.getReferenceById(request.userAccountDto().userId());
        Comment comment = commentRepository.findById(commentId).orElseThrow(RuntimeException::new);     //TODO: 예외 처리 구현 필요
        if (comment.getUserAccount().equals(userAccount)) {
            if (request.content() != null) {
                comment.setContent(request.content());
            }
        }
    }

    public void deleteComment(Long commentId, Long postId, String userId) {
        commentRepository.deleteByIdAndPost_IdAndUserAccount_UserId(commentId, postId, userId);
    }
}
