package com.teammatching.demo.web.service;

import com.teammatching.demo.domain.dto.TeamDto;
import com.teammatching.demo.domain.entity.Team;
import com.teammatching.demo.domain.entity.UserAccount;
import com.teammatching.demo.domain.repository.TeamRepository;
import com.teammatching.demo.domain.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserAccountRepository userAccountRepository;

    public Page<TeamDto> getSimpleTeams(Pageable pageable) {
        return teamRepository.findAll(pageable).map(TeamDto::from);
    }

    public void createTeam(TeamDto request, String userId) {
        UserAccount userAccount = userAccountRepository.getReferenceById(userId);
        if (request.name() == null) throw new RuntimeException("nullable = false");     //TODO: 예외 처리 구현 필요
        if (request.hashtag() == null) throw new RuntimeException("nullable = false");  //TODO: 예외 처리 구현 필요
        if (request.capacity() == null) throw new RuntimeException("nullable = false"); //TODO: 예외 처리 구현 필요
        if (request.total() == null) throw new RuntimeException("nullable = false");    //TODO: 예외 처리 구현 필요
        teamRepository.save(request.toEntity(userAccount));
    }

    public TeamDto getTeamById(Long teamId) {
        return TeamDto.from(findTeamById(teamId));
    }

    public void updateTeam(Long teamId, TeamDto request, String userId) {
        Team team = findTeamById(teamId);
        if (request.adminId().equals(userId)) {
            if (request.name() != null) team.setName(request.name());
            if (request.category() != null) team.setCategory(request.category());
            if (request.hashtag() != null) team.setHashtag(request.hashtag());
            if (request.total() != null) team.setTotal(request.total());
            team.setDescription(request.description());
            team.setCapacity(request.capacity());
        }
    }

    public void deleteTeam(Long teamId, String userId) {
        teamRepository.deleteByIdAndAdminId(teamId, userId);
    }

    private Team findTeamById(Long teamId) {
        return teamRepository.findById(teamId).orElseThrow(RuntimeException::new);      //TODO: 예외 처리 구현 필요
    }
}
