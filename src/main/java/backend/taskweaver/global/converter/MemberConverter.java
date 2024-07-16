package backend.taskweaver.global.converter;

import backend.taskweaver.domain.member.dto.*;
import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.member.entity.enums.LoginType;
import backend.taskweaver.domain.notification.entity.enums.isRead;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MemberConverter {

    public static Member toMember(SignUpRequest signUpRequest, PasswordEncoder encoder) {
        return Member.builder()
                .email(signUpRequest.email())
                .password(encoder.encode(signUpRequest.password()))
                .nickname(signUpRequest.nickname())
                .loginType(LoginType.DEFAULT)
                .imageUrl(signUpRequest.imageUrl())
//                .isRead(isRead.YES)
                .build();
    }

    public static SignUpResponse toSignUpResponse(Member member) {
        return new SignUpResponse(
                member.getId(),
                member.getEmail(),
                member.getNickname()
                // 이미지 추가하기
        );
    }

    public static SignInResponse toSignInResponse(Member member, String accessToken, String refreshToken) {
        return new SignInResponse(
                member.getId(),
                member.getEmail(),
                member.getLoginType(),
                member.getNickname(),
//                member.getIsRead(),
                member.getImageUrl(),
                accessToken,
                refreshToken
        );
    }

    public static MemberInfoResponse toMemberInfoResponse(Member member) {
        return new MemberInfoResponse(
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                member.getLoginType(),
                member.getImageUrl()
        );
    }

    public static CreateAccessTokenResponse toCreateAccessTokenResponse(String newAccessToken) {
        return new CreateAccessTokenResponse(newAccessToken);
    }
}
