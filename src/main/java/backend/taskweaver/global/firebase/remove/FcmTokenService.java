//package backend.taskweaver.global.firebase;
//
//import backend.taskweaver.domain.member.dto.DeviceTokenRequest;
//import backend.taskweaver.domain.member.entity.Member;
//import backend.taskweaver.domain.member.repository.MemberRepository;
//import backend.taskweaver.global.code.ErrorCode;
//import backend.taskweaver.global.exception.handler.BusinessExceptionHandler;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class FcmTokenService {
//
//    private final MemberRepository memberRepository;
//    private final FcmTokenRepository fcmTokenRepository;
//
//    @Transactional
//    public void save(DeviceTokenRequest request, Long memberId) {
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.MEMBER_NOT_FOUND));
//        fcmTokenRepository.saveToken(member.getEmail(), request.deviceToken());
//    }
//}
