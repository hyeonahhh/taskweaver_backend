package backend.taskweaver.domain.member.dto;

import backend.taskweaver.domain.member.entity.LoginType;
import backend.taskweaver.domain.member.entity.Member;

public record SignUpResponse(
        String email,
        String nickname,
        LoginType loginType,
        String imageUrl
) {
    public static SignUpResponse from(Member member) {
        return new SignUpResponse(
                member.getEmail(),
                member.getNickname(),
                member.getLoginType(),
                member.getImageUrl()
        );
    }
}
