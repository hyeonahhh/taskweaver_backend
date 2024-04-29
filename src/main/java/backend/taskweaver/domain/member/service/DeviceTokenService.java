package backend.taskweaver.domain.member.service;

import backend.taskweaver.domain.member.dto.DeviceTokenRequest;
import backend.taskweaver.domain.member.entity.DeviceToken;
import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.member.repository.DeviceTokenRepository;
import backend.taskweaver.domain.member.repository.MemberRepository;
import backend.taskweaver.domain.project.dto.ProjectResponse;
import backend.taskweaver.global.code.ErrorCode;
import backend.taskweaver.global.converter.MemberConverter;
import backend.taskweaver.global.converter.ProjectConverter;
import backend.taskweaver.global.exception.handler.BusinessExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeviceTokenService {

    private final DeviceTokenRepository deviceTokenRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void save(DeviceTokenRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.MEMBER_NOT_FOUND));

        // 새로운 디바이스 토큰만 저장 -> 이미 저장되어 있는데 또 저장하면 같은 기기에 같은 푸시 알림이 여러 번 감
        if(deviceTokenRepository.findByDeviceToken(request.deviceToken()).isEmpty()) {
            DeviceToken deviceToken = MemberConverter.toDeviceToken(request, member);
            deviceTokenRepository.save(deviceToken);
        }
    }
}
