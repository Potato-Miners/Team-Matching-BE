package com.teammatching.demo.web.service;

import com.teammatching.demo.domain.dto.CommentDto;
import com.teammatching.demo.domain.dto.PostDto;
import com.teammatching.demo.domain.dto.TeamDto;
import com.teammatching.demo.domain.dto.UserAccountDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class MyPageService {
    public UserAccountDto getMyPage(String userId, UserAccountDto build) {
        return null;
    }

    public void updateAccount(String userId, UserAccountDto.UpdateRequest request, UserAccountDto build) {
    }

    public Page<PostDto> getMyPosts(String userId, UserAccountDto build) {
        return null;
    }

    public Page<CommentDto> getMyComments(String userId, UserAccountDto build) {
        return null;
    }

    public Page<TeamDto> getMyTeams(String userId, UserAccountDto build) {
        return null;
    }
}
