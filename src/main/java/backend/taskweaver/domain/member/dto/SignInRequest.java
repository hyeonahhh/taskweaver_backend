package backend.taskweaver.domain.member.dto;


import jakarta.validation.constraints.NotNull;

public record SignInRequest(
        @NotNull
        String email,

        @NotNull
        String password
) {
}
