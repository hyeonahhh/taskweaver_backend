package backend.taskweaver.domain.project.dto;

import jakarta.validation.constraints.NotNull;

public record ProjectRequest(
        @NotNull
        String name,

        @NotNull
        String description,

        @NotNull
        Long managerId
) {
}
