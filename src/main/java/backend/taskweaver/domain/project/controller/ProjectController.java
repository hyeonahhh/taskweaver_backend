package backend.taskweaver.domain.project.controller;

import backend.taskweaver.domain.project.dto.ProjectRequest;
import backend.taskweaver.domain.project.service.ProjectServiceImpl;
import backend.taskweaver.global.code.ApiResponse;
import backend.taskweaver.global.code.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ProjectController {

    private final ProjectServiceImpl projectService;

    @PostMapping("/team/{teamId}/project")
    public ResponseEntity<ApiResponse> addProject(@RequestBody @Valid ProjectRequest request,
                                                  @PathVariable Long teamId) {
        ApiResponse apiResponse = ApiResponse.builder()
                .result(projectService.createProject(request, teamId))
                .resultCode(SuccessCode.INSERT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.INSERT_SUCCESS.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(apiResponse);
    }
}
