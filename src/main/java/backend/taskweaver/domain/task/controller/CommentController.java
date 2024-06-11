package backend.taskweaver.domain.task.controller;

import backend.taskweaver.domain.task.dto.CommentRequest;
import backend.taskweaver.domain.task.dto.CommentResponse;
import backend.taskweaver.domain.task.dto.UpdateCommentRequest;
import backend.taskweaver.domain.task.service.CommentService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Tag(name = "태스크 댓글 관련 api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/task/{taskId}/comment")
    @Operation(summary = "댓글 등록 api", description = "댓글을 등록하는 api입니다.")
    public ResponseEntity<ApiResponse> createComment(@RequestBody @Valid CommentRequest request,
                                                     @PathVariable @Parameter(description = "태스크 ID") Long taskId,
                                                     @AuthenticationPrincipal User user) {
        ApiResponse apiResponse = ApiResponse.<CommentResponse>builder()
                .result(commentService.create(request, taskId, Long.parseLong(user.getUsername())))
                .resultCode(SuccessCode.INSERT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.INSERT_SUCCESS.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(apiResponse);
    }

    @PatchMapping("/comment/{commentId}")
    @Operation(summary = "댓글 수정 api", description = "댓글을 수정하는 api입니다.")
    public ResponseEntity<ApiResponse> createComment(@RequestBody @Valid UpdateCommentRequest request,
                                                     @PathVariable @Parameter(description = "댓글 ID") Long commentId,
                                                     @AuthenticationPrincipal User user) {
        commentService.update(request, commentId, Long.parseLong(user.getUsername()));
        ApiResponse apiResponse = ApiResponse.builder()
                .resultCode(SuccessCode.UPDATE_SUCCESS.getStatus())
                .resultMsg(SuccessCode.UPDATE_SUCCESS.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(apiResponse);
    }

    @GetMapping("/task/{taskId}/comment")
    @Operation(summary = "댓글 조회 api", description = "댓글을 조회하는 api입니다.")
    public ResponseEntity<ApiResponse> getComment(@PathVariable @Parameter(description = "태스크 ID") Long taskId) {
        ApiResponse apiResponse = ApiResponse.builder()
                .result(commentService.get(taskId))
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(apiResponse);
    }
}
