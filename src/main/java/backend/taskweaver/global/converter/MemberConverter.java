package backend.taskweaver.global.converter;

import backend.taskweaver.domain.member.dto.MemberInfoResponse;
import backend.taskweaver.domain.member.dto.SignInResponse;
import backend.taskweaver.domain.member.dto.SignUpRequest;
import backend.taskweaver.domain.member.dto.SignUpResponse;
import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.member.entity.enums.LoginType;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MemberConverter {

    public static Member toMember(SignUpRequest signUpRequest, PasswordEncoder encoder) {
        return Member.builder()
                .email(signUpRequest.email())
                .password(encoder.encode(signUpRequest.password()))
                .nickname(signUpRequest.nickname())
                .loginType(LoginType.DEFAULT)
                .imageUrl(signUpRequest.imageUrl())
                .build();
    }
    public static SignUpResponse toSignUpResponse(Member member) {
        return new SignUpResponse(
                member.getId(),
                member.getEmail(),
                member.getNickname()
        );
    }

    public static SignInResponse toSignInResponse(Member member, String accessToken, String refreshToken) {
        return new SignInResponse(
                member.getId(),
                member.getEmail(),
                member.getLoginType(),
                member.getNickname(),
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
}
