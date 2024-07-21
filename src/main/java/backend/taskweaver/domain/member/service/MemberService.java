package backend.taskweaver.domain.member.service;

import backend.taskweaver.domain.files.service.S3Service;
import backend.taskweaver.domain.member.dto.*;
import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.member.repository.MemberRefreshTokenRepository;
import backend.taskweaver.domain.member.repository.MemberRepository;
import backend.taskweaver.global.code.ErrorCode;
import backend.taskweaver.global.converter.MemberConverter;
import backend.taskweaver.global.exception.handler.BusinessExceptionHandler;
import backend.taskweaver.global.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberRefreshTokenRepository memberRefreshTokenRepository;
    private final PasswordEncoder encoder;
    private final TokenProvider tokenProvider;
    private final S3Service s3Service;


    @Transactional(readOnly = true)
    public MemberInfoResponse getMemberInfo(Long id) {
        return memberRepository.findById(id)
                .map(MemberConverter::toMemberInfoResponse)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }


    // 회원정보 수정
    @Transactional
    public void updateMember(Long memberId, UpdateMemberRequest request, MultipartFile profileImage) throws IOException {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.MEMBER_NOT_FOUND));

        member.setNickname(request.nickname());

        // 프로필 이미지 수정
        if (profileImage != null && !profileImage.isEmpty()) {
            // S3에 새 프로필 이미지 업로드
            String imageUrl = s3Service.saveProfileImage(profileImage);
            member.setImageUrl(imageUrl); // 이미지 URL 업데이트
        }


    }

    @Transactional
    public void updatePassword(Long memberId, UpdatePasswordRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.MEMBER_NOT_FOUND));

        // 비밀번호가 같은지 확인
        if(!encoder.matches(request.oldPassword(), member.getPassword())) {
            throw new BusinessExceptionHandler(ErrorCode.PASSWORD_NOT_MATCH);
        }

        // 현재 비밀번호와 같은 비밀번호로 변경 불가능
        if(request.oldPassword().equals(request.newPassword())) {
            throw new BusinessExceptionHandler(ErrorCode.SAME_PASSWORD);
        }

        member.updatePassword(encoder.encode(request.newPassword()));
    }
}