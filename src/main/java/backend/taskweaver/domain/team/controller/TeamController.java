package backend.taskweaver.domain.team.controller;

import backend.taskweaver.domain.team.dto.TeamInviteRequest;
import backend.taskweaver.domain.team.dto.TeamRequest;
import backend.taskweaver.domain.team.service.TeamService;
import backend.taskweaver.global.code.ApiResponse;
import backend.taskweaver.global.code.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1")
@Tag(name = "팀 관련")
@RequiredArgsConstructor
@Slf4j
@RestController
@EnableJpaAuditing
public class TeamController {

    private final TeamService teamService;

    @Operation(summary = "팀 생성")
    @PostMapping("/team")
    public ResponseEntity<ApiResponse> createTeam(@RequestBody TeamRequest.teamCreateRequest request, @AuthenticationPrincipal User user) {
        try {
            ApiResponse ar = ApiResponse.builder()
                    .result(teamService.createTeam(request, Long.parseLong(user.getUsername())))
                    .resultCode(SuccessCode.INSERT_SUCCESS.getStatus())
                    .resultMsg(SuccessCode.INSERT_SUCCESS.getMessage())
                    .build();
            return new ResponseEntity<>(ar, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "팀 조회")
    @GetMapping("/team/{teamId}")
    public ResponseEntity<ApiResponse> findTeam(@PathVariable(name = "teamId") Long teamId) {

        ApiResponse ar = ApiResponse.builder()
                .result(teamService.findTeam(teamId))
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();
        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    // 팀 멤버만 조회
    @Operation(summary = "팀 조회")
    @GetMapping("/team/{teamId}/member")
    public ResponseEntity<ApiResponse> AllTeamMember(@PathVariable(name = "teamId") Long teamId) {
        ApiResponse ar = ApiResponse.builder()
                .result(teamService.findTeam(teamId))
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();
        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @Operation(summary =  "팀원 삭제")
    @PostMapping("/team/{teamId}/delete")
    public ResponseEntity<ApiResponse> deleteTeamMembers(@PathVariable Long teamId, @RequestBody TeamRequest.teamDeleteRequest request) {
        teamService.deleteTeamMembers(teamId, request.getMemberId()); // deleteTeamMembers 메서드 직접 호출
        ApiResponse ar = ApiResponse.builder()
                .resultCode(SuccessCode.DELETE_SUCCESS.getStatus())
                .resultMsg(SuccessCode.DELETE_SUCCESS.getMessage())
                .build();
        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @Operation(summary = "팀원 초대 - 이메일로 초대")
    @PostMapping("/team/invitation/email")
    public ResponseEntity<ApiResponse> inviteEmail(@RequestBody TeamInviteRequest.EmailInviteRequest request) {
        ApiResponse ar = ApiResponse.builder()
                .result(teamService.inviteEmail(request))
                .resultCode(SuccessCode.INSERT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.INSERT_SUCCESS.getMessage())
                .build();
        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @Operation(summary =  "팀원 초대 - 이메일로 초대 응답")
    @PostMapping("/team/invitation/answer")
    public ResponseEntity<ApiResponse> answerInviteEmail(@RequestBody TeamInviteRequest.InviteAnswerRequest request, @AuthenticationPrincipal User user) {
        ApiResponse ar = ApiResponse.builder()
                .result(teamService.answerInvite(request, Long.parseLong(user.getUsername())))
                .resultCode(SuccessCode.INSERT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.INSERT_SUCCESS.getMessage())
                .build();
        return new ResponseEntity<>(ar, HttpStatus.OK);
    }



}
