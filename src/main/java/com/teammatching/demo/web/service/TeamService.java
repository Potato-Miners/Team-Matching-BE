package com.teammatching.demo.web.service;

import com.teammatching.demo.domain.dto.TeamDto;
import com.teammatching.demo.domain.dto.UserAccountDto;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
    public TeamDto.SimpleResponse getSimpleTeams() {
        return null;
    }

    public void createTeam(TeamDto toDto, UserAccountDto build) {
    }

    public TeamDto getTeamById(Long teamId) {
        return null;
    }

    public void updateTeam(Long teamId, TeamDto toDto, UserAccountDto build) {
    }

    public void deleteTeam(Long teamId, UserAccountDto build) {
    }
}
