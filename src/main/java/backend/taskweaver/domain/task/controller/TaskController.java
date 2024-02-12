package backend.taskweaver.domain.task.controller;

import backend.taskweaver.domain.project.repository.ProjectRepository;
import backend.taskweaver.domain.task.dto.TaskRequest;
import backend.taskweaver.domain.task.repository.TaskRepository;
import backend.taskweaver.domain.task.service.TaskService;
import backend.taskweaver.domain.team.dto.TeamRequest;
import backend.taskweaver.domain.team.service.TeamService;
import backend.taskweaver.global.code.ApiResponse;
import backend.taskweaver.global.code.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1")
@Tag(name = "태스크 관련")
@RequiredArgsConstructor
@Slf4j
@RestController
public class TaskController {

    private final TaskService taskService;


    @Operation(summary = "태스크 생성")
    @PostMapping("/projects/{projectId}/tasks/{parentTaskId}")
    public ResponseEntity<ApiResponse> createTask(@RequestBody TaskRequest.taskCreate request, @AuthenticationPrincipal User user, @PathVariable Long projectId, @PathVariable(required = false) Long parentTaskId) {
        try {
            ApiResponse ar = ApiResponse.builder()
                    .result(taskService.createTask(request, Long.parseLong(user.getUsername()), projectId, parentTaskId))
                    .resultCode(SuccessCode.INSERT_SUCCESS.getStatus())
                    .resultMsg(SuccessCode.INSERT_SUCCESS.getMessage())
                    .build();
            return new ResponseEntity<>(ar, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
