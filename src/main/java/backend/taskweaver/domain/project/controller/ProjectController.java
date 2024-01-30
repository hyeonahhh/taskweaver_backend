package backend.taskweaver.domain.project.controller;

import backend.taskweaver.domain.project.dto.ProjectRequest;
import backend.taskweaver.domain.project.dto.ProjectResponse;
import backend.taskweaver.domain.project.service.ProjectServiceImpl;
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
    public ResponseEntity<ProjectResponse> addProject(@RequestBody @Valid ProjectRequest request,
                                                      @PathVariable Long teamId) {
        ProjectResponse response = projectService.createProject(request, teamId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

}
