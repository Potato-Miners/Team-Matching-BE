package com.teammatching.demo.application.MyPage;

import com.teammatching.demo.domain.dto.CommentWithPostDto;
import com.teammatching.demo.domain.dto.PostDto;
import com.teammatching.demo.domain.dto.TeamDto;
import com.teammatching.demo.domain.dto.UserAccountDto;
import com.teammatching.demo.domain.entity.UserAccount;
import com.teammatching.demo.domain.repository.AdmissionRepository;
import com.teammatching.demo.domain.repository.CommentRepository;
import com.teammatching.demo.domain.repository.PostRepository;
import com.teammatching.demo.domain.repository.UserAccountRepository;
import com.teammatching.demo.global.exception.NotEqualsException;
import com.teammatching.demo.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional
@Service
public class MyPageService {

    private final UserAccountRepository userAccountRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AdmissionRepository admissionRepository;

    @Transactional(readOnly = true)
    public UserAccountDto getMyPage(String userId, String authenticatedUserId) {
        if (userId.equals(authenticatedUserId)) {
            return UserAccountDto.from(findUserAccountById(userId));
        } else {
            throw new NotEqualsException.UserAccount();
        }
    }

    public void updateAccountInfo(String userId, UserAccountDto request, String authenticatedUserId) {
        UserAccount userAccount = findUserAccountById(userId);
        if (userId.equals(authenticatedUserId)) {
            userAccount.setNickname(request.nickname());
            userAccount.setMemo(request.memo());
        }
    }

    @Transactional(readOnly = true)
    public Page<PostDto> getMyPosts(String userId, String authenticatedUserId, Pageable pageable) {
        if (userId.equals(authenticatedUserId)) {
            return postRepository.findByUserAccount_UserId(userId, pageable).map(PostDto::from);
        } else {
            throw new NotEqualsException.UserAccount();
        }
    }

    @Transactional(readOnly = true)
    public Page<CommentWithPostDto> getMyComments(String userId, String authenticatedUserId, Pageable pageable) {
        if (userId.equals(authenticatedUserId)) {
            return commentRepository.findByUserAccount_UserId(userId, pageable).map(CommentWithPostDto::from);
        } else {
            throw new NotEqualsException.UserAccount();
        }
    }

    @Transactional(readOnly = true)
    public Page<TeamDto> getMyTeams(String userId, String authenticatedUserId, Pageable pageable) {
        if (userId.equals(authenticatedUserId)) {
            return admissionRepository.findMyTeams(userId, pageable)
                    .map(admission -> TeamDto.from(admission.getTeam()));
        } else {
            throw new NotEqualsException.UserAccount();
        }
    }

    @Transactional(readOnly = true)
    public Page<TeamDto> getMyJudgingTeams(String userId, String authenticatedUserId, Pageable
            pageable) {
        if (userId.equals(authenticatedUserId)) {
            return admissionRepository.findMyJudgingTeams(userId, pageable)
                    .map(admission -> TeamDto.from(admission.getTeam()));
        } else {
            throw new NotEqualsException.UserAccount();
        }
    }

    private UserAccount findUserAccountById(String userId) {
        return userAccountRepository.findById(userId)
                .orElseThrow(NotFoundException.UserAccount::new);
    }
}
