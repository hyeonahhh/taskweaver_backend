package backend.taskweaver.domain.project.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateStateRequest(
        @NotNull
        String projectState
) {
}
