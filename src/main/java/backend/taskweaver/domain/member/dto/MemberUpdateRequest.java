package backend.taskweaver.domain.member.dto;


public record MemberUpdateRequest(
        String password,
        String nickname,
        String ImageUrl
) {
}
