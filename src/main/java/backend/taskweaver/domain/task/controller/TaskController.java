package backend.taskweaver.domain.task.controller;

import backend.taskweaver.domain.task.dto.TaskRequest;
import backend.taskweaver.domain.task.service.TaskService;
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
    @PostMapping("/projects/{projectId}/tasks")
    public ResponseEntity<ApiResponse> createTask(@RequestBody TaskRequest.taskCreate request, @AuthenticationPrincipal User user, @PathVariable Long projectId) {
        try {
            ApiResponse ar = ApiResponse.builder()
                    .result(taskService.createTask(request, Long.parseLong(user.getUsername()), projectId))
                    .resultCode(SuccessCode.INSERT_SUCCESS.getStatus())
                    .resultMsg(SuccessCode.INSERT_SUCCESS.getMessage())
                    .build();
            return new ResponseEntity<>(ar, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Operation(summary = "태스크 삭제")
    @DeleteMapping("/task/{taskId}")
    public ResponseEntity<ApiResponse> deleteTask(@AuthenticationPrincipal User user, @PathVariable Long taskId) {
        try {
            ApiResponse ar = ApiResponse.builder()
                    .result(taskService.deleteTask(Long.parseLong(user.getUsername()), taskId))
                    .resultCode(SuccessCode.DELETE_SUCCESS.getStatus())
                    .resultMsg(SuccessCode.DELETE_SUCCESS.getMessage())
                    .build();
            return new ResponseEntity<>(ar, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "태스크 상태 변경")
    @PatchMapping("/task/state/{taskId}")
    public ResponseEntity<ApiResponse> changeTaskState(@RequestBody TaskRequest.taskStateChange request, @AuthenticationPrincipal User user, @PathVariable Long taskId) {
        try {
            ApiResponse ar = ApiResponse.builder()
                    .result(taskService.changeTaskState(request, Long.parseLong(user.getUsername()), taskId))
                    .resultCode(SuccessCode.UPDATE_SUCCESS.getStatus())
                    .resultMsg(SuccessCode.UPDATE_SUCCESS.getMessage())
                    .build();
            return new ResponseEntity<>(ar, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "태스크 수정")
    @PatchMapping("/task/{taskId}")
    public ResponseEntity<ApiResponse> changeTask(@RequestBody TaskRequest.taskChange request, @AuthenticationPrincipal User user, @PathVariable Long taskId) {
        try {
            ApiResponse ar = ApiResponse.builder()
                    .result(taskService.changeTask(request, Long.parseLong(user.getUsername()), taskId))
                    .resultCode(SuccessCode.UPDATE_SUCCESS.getStatus())
                    .resultMsg(SuccessCode.UPDATE_SUCCESS.getMessage())
                    .build();
            return new ResponseEntity<>(ar, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
