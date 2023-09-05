package com.teammatching.demo.web.service;

import com.teammatching.demo.domain.dto.CommentDto;
import com.teammatching.demo.domain.entity.Comment;
import com.teammatching.demo.domain.entity.Post;
import com.teammatching.demo.domain.entity.UserAccount;
import com.teammatching.demo.domain.repository.CommentRepository;
import com.teammatching.demo.domain.repository.PostRepository;
import com.teammatching.demo.domain.repository.UserAccountRepository;
import com.teammatching.demo.result.exception.NotFoundException;
import com.teammatching.demo.result.exception.NullCheckException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserAccountRepository userAccountRepository;


    @Transactional(readOnly = true)
    public List<CommentDto> getComments() {
        return commentRepository.findAll().stream()
                .map(CommentDto::from)
                .collect(Collectors.toList());
    }

    public void createComment(Long postId, CommentDto request) {
        UserAccount userAccount = userAccountRepository.getReferenceById(request.userAccountDto().userId());
        Post post = postRepository.getReferenceById(postId);
        if (request.content() == null) throw new NullCheckException("Comment.content");
        commentRepository.save(request.toEntity(post, userAccount));
    }

    public void updateComment(Long commentId, CommentDto request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(NotFoundException.Comment::new);
        if (comment.getUserAccount().getUserId().equals(request.userAccountDto().userId())) {
            if (request.content() != null) comment.setContent(request.content());
        }
    }

    public void deleteComment(Long commentId, Long postId, String userId) {
        commentRepository.deleteByIdAndPost_IdAndUserAccount_UserId(commentId, postId, userId);
    }
}
