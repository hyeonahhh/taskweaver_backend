package backend.taskweaver.domain.project.controller;

import backend.taskweaver.domain.project.dto.ProjectRequest;
import backend.taskweaver.domain.project.dto.ProjectResponse;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Tag(name = "프로젝트 관련 api")
public class ProjectController {

    private final ProjectServiceImpl projectService;

    // todo: 응답부분 swagger 고치기
    
    @PostMapping("/team/{teamId}/project")
    @Operation(summary = "프로젝트 등록 메서드", description = "프로젝트 등록 메서드입니다.")
    //@ApiResponse(responseCode = "201", description = "프로젝트 등록 성공", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
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
}
