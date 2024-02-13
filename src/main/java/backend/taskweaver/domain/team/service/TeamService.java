package backend.taskweaver.domain.team.service;


import backend.taskweaver.domain.team.dto.TeamInviteRequest;
import backend.taskweaver.domain.team.dto.TeamInviteResponse;
import backend.taskweaver.domain.team.dto.TeamRequest;
import backend.taskweaver.domain.team.dto.TeamResponse;
import backend.taskweaver.domain.team.entity.TeamMember;

import java.util.List;


public interface TeamService {

    // 우선 팀 생성자 필드로만 추가
    public TeamResponse.teamCreateResult createTeam(TeamRequest.teamCreateRequest request, Long user);
    public TeamResponse.findTeamResult findTeam(Long id);
    public TeamInviteRequest.EmailInviteRequest inviteEmail(TeamInviteRequest.EmailInviteRequest request);

    public TeamInviteResponse.InviteAnswerResult answerInvite(TeamInviteRequest.InviteAnswerRequest request, Long user);

    public void deleteTeamMembers(Long teamId, List<Long> memberIds);
}
