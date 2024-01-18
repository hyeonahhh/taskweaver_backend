package backend.taskweaver.domain.member.service;

import backend.taskweaver.domain.member.dto.MemberDeleteResponse;
import backend.taskweaver.domain.member.dto.MemberInfoResponse;
import backend.taskweaver.domain.member.dto.MemberUpdateRequest;
import backend.taskweaver.domain.member.dto.MemberUpdateResponse;
import backend.taskweaver.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    @Transactional(readOnly = true)
    public MemberInfoResponse getMemberInfo(Long id) {
        return memberRepository.findById(id)
                .map(MemberInfoResponse::from)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }

    // softDelete로 바꾸기
    @Transactional
    public MemberDeleteResponse deleteMember(Long id) {
        if (!memberRepository.existsById(id)) return new MemberDeleteResponse(false);
        memberRepository.deleteById(id);
        return new MemberDeleteResponse(true);
    }

    @Transactional
    public MemberUpdateResponse updateMember(Long id, MemberUpdateRequest request) {
        return memberRepository.findById(id)
                .filter(member -> member.getPassword().equals(request.password()))
                .map(member -> {
                    member.update(request, encoder);	// 새 비밀번호를 암호화하도록 수정
                    return MemberUpdateResponse.of(true, member);
                })
                .orElseThrow(() -> new NoSuchElementException("아이디 또는 비밀번호가 일치하지 않습니다."));
    }
}
