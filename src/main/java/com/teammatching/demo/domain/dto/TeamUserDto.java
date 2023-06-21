package com.teammatching.demo.domain.dto;

import com.teammatching.demo.domain.entity.Team;
import com.teammatching.demo.domain.entity.TeamUser;
import com.teammatching.demo.domain.entity.UserAccount;
import lombok.Builder;

@Builder
public record TeamUserDto(
        Long id,
        Long teamId,
        UserAccountDto userAccountDto
) {

    public TeamUserDto from(TeamUser entity) {
        return TeamUserDto.builder()
                .id(entity.getId())
                .teamId(entity.getTeam().getId())
                .userAccountDto(UserAccountDto.from(entity.getUserAccount()))
                .build();
    }

    public TeamUser toEntity(UserAccount userAccount, Team team) {
        return TeamUser.builder()
                .userAccount(userAccount)
                .team(team)
                .build();
    }

}
