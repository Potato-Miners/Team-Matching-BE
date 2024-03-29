package com.teammatching.demo.application.Team;

import com.teammatching.demo.domain.Category;
import com.teammatching.demo.domain.dto.AdmissionDto;
import com.teammatching.demo.domain.dto.TeamDto;
import com.teammatching.demo.domain.entity.Team;
import com.teammatching.demo.domain.entity.UserAccount;
import com.teammatching.demo.domain.repository.AdmissionRepository;
import com.teammatching.demo.domain.repository.TeamRepository;
import com.teammatching.demo.domain.repository.UserAccountRepository;
import com.teammatching.demo.global.exception.NotFoundException;
import com.teammatching.demo.global.exception.NullCheckException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final AdmissionRepository admissionRepository;
    private final UserAccountRepository userAccountRepository;

    @Transactional(readOnly = true)
    public List<TeamDto> getTeams() {
        return teamRepository.findAll().stream()
                .map(TeamDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<TeamDto> getSimpleTeams(Pageable pageable) {
        return teamRepository.findAll(pageable).map(TeamDto::from);
    }

    @Transactional(readOnly = true)
    public Page<TeamDto> getSimpleTeamsByCategory(Category category, Pageable pageable) {
        return teamRepository.findByCategory(category, pageable).map(TeamDto::from);
    }

    @Transactional(readOnly = true)
    public Page<TeamDto> getSearchTeams(String keyword, Pageable pageable) {
        return teamRepository.searchAllByFields(keyword, pageable).map(TeamDto::from);
    }

    public void createTeam(TeamDto request, String userId) {
        UserAccount userAccount = userAccountRepository.getReferenceById(userId);
        if (request.name() == null) throw new NullCheckException("Team.name");
        if (request.hashtag() == null) throw new NullCheckException("Team.hashtag");
        if (request.capacity() == null) throw new NullCheckException("Team.capacity");
        if (request.total() == null) throw new NullCheckException("Team.total");
        Team team = teamRepository.save(request.toEntity(userAccount));
        AdmissionDto admission = AdmissionDto.builder()
                .approval(true)
                .build();
        admissionRepository.save(admission.toEntity(userAccount, team));
    }

    public TeamDto getTeamById(Long teamId) {
        return TeamDto.from(findTeamById(teamId));
    }

    public void updateTeam(Long teamId, TeamDto request, String userId) {
        Team team = findTeamById(teamId);
        if (team.getAdminUserAccount().getUserId().equals(userId)) {
            if (request.name() != null) team.setName(request.name());
            if (request.category() != null) team.setCategory(request.category());
            if (request.hashtag() != null) team.setHashtag(request.hashtag());
            if (request.capacity() != null) team.setCapacity(request.capacity());
            team.setDescription(request.description());
            team.setDeadline(request.deadline());
        }
    }

    public void deleteTeam(Long teamId, String userId) {
        teamRepository.deleteByIdAndAdminUserAccount_UserId(teamId, userId);
    }

    private Team findTeamById(Long teamId) {
        return teamRepository.findById(teamId).orElseThrow(NotFoundException.Team::new);
    }
}
