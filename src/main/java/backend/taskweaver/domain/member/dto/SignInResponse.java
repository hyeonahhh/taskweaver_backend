package backend.taskweaver.domain.member.dto;

public record SignInResponse(
        String email,
        String nickname,
        String accessToken,
        String refreshToken
) {
}
