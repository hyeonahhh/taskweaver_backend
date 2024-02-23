package backend.taskweaver.domain.project.controller;

import backend.taskweaver.domain.project.dto.GetAllProjectResponse;
import backend.taskweaver.domain.project.dto.ProjectRequest;
import backend.taskweaver.domain.project.dto.ProjectResponse;
import backend.taskweaver.domain.project.dto.UpdateStateRequest;
import backend.taskweaver.domain.project.service.ProjectServiceImpl;
import backend.taskweaver.global.code.ApiResponse;
import backend.taskweaver.global.code.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Tag(name = "프로젝트 관련 api")
public class ProjectController {

    private final ProjectServiceImpl projectService;

    // todo: 응답부분 swagger 고치기
    
    @PostMapping("/team/{teamId}/project")
    @Operation(summary = "프로젝트 등록 메서드", description = "프로젝트 등록 api입니다.")
    public ResponseEntity<ApiResponse> addProject(@RequestBody @Valid ProjectRequest request,
                                                  @PathVariable @Parameter(description = "팀 ID") Long teamId) {
        ApiResponse apiResponse = ApiResponse.<ProjectResponse>builder()
                .result(projectService.createProject(request, teamId))
                .resultCode(SuccessCode.INSERT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.INSERT_SUCCESS.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(apiResponse);
    }

    @GetMapping("/team/{teamId}/projects")
    @Operation(summary = "프로젝트 전체 조회 메서드", description = "팀 내 모든 프로젝트를 조회하는 api입니다.")
    public ResponseEntity<ApiResponse> getAllProject(@PathVariable @Parameter(description = "팀 ID") Long teamId) {
        ApiResponse apiResponse = ApiResponse.<List<GetAllProjectResponse>>builder()
                .result(projectService.getAll(teamId))
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(apiResponse);
    }

    @GetMapping("/project/{projectId}")
    @Operation(summary = "프로젝트 상세 조회 메서드", description = "한 프로젝트에 대해 상세 조회하는 api입니다.")
    public ResponseEntity<ApiResponse> getOneProject(@PathVariable @Parameter(description = "프로젝트 ID")Long projectId) {
        ApiResponse apiResponse = ApiResponse.<ProjectResponse>builder()
                .result(projectService.getOne(projectId))
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(apiResponse);
    }

    @PatchMapping("/project/{projectId}/state")
    @Operation(summary = "프로젝트 상태 변경 메서드", description = "프로젝트의 상태를 변경하는 api입니다.")
    public ResponseEntity<ApiResponse> updateState(@PathVariable @Parameter(description = "프로젝트 ID") Long projectId,
                                                   @RequestBody @Valid UpdateStateRequest request,
                                                   @AuthenticationPrincipal User user) {
        projectService.updateState(projectId, request, Long.parseLong(user.getUsername()));
        ApiResponse apiResponse = ApiResponse.<ProjectResponse>builder()
                .resultCode(SuccessCode.UPDATE_SUCCESS.getStatus())
                .resultMsg(SuccessCode.UPDATE_SUCCESS.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(apiResponse);
    }
}
