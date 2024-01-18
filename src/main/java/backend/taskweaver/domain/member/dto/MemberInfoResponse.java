package backend.taskweaver.domain.member.dto;

import backend.taskweaver.domain.member.entity.LoginType;
import backend.taskweaver.domain.member.entity.Member;

public record MemberInfoResponse(
        Long id,
        String email,
        String nickname,
        LoginType loginType,
        String imageUrl
) {
    public static MemberInfoResponse from(Member member) {
        return new MemberInfoResponse(
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                member.getLoginType(),
                member.getImageUrl()
        );
    }
}
