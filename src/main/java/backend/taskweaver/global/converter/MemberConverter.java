package backend.taskweaver.global.converter;

import backend.taskweaver.domain.member.dto.*;
import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.member.entity.enums.LoginType;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MemberConverter {

    public static Member toMember(SignUpRequest signUpRequest, PasswordEncoder encoder, String profileImage) {
        String imageUrl = null;
        if (profileImage != null && !profileImage.isEmpty()) {
            imageUrl = profileImage;
        }

        return Member.builder()
                .email(signUpRequest.email())
                .password(encoder.encode(signUpRequest.password()))
                .nickname(signUpRequest.nickname())
                .loginType(LoginType.DEFAULT)
                .imageUrl(imageUrl)
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
