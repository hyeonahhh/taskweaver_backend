package backend.taskweaver.domain.member.dto;

import backend.taskweaver.domain.member.entity.Member;

public record MemberUpdateResponse(
        boolean result, // 회원 정보 수정 성공 여부
        //String password, // 수정시 화면에 비밀번호 안 보내줌
        String nickname,
        String ImageUrl
) {
    public static MemberUpdateResponse of(boolean result, Member member) {
        return new MemberUpdateResponse(result, member.getNickname(), member.getImageUrl());
    }
}
