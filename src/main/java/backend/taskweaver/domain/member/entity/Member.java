package backend.taskweaver.domain.member.entity;

import backend.taskweaver.domain.member.dto.MemberUpdateRequest;
import backend.taskweaver.domain.member.dto.SignUpRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "member")
@Builder
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "login_type", nullable = false)
    private LoginType loginType;

    @Column(name = "image_url")
    private String imageUrl;

    public static Member from(SignUpRequest request, PasswordEncoder encoder) {	// 파라미터에 PasswordEncoder 추가
        return Member.builder()
                .email(request.email())
                .password(encoder.encode(request.password()))	// 수정
                .nickname(request.nickname())
                .loginType(LoginType.DEFAULT)
                .imageUrl(request.ImageUrl())
                .build();
    }

    public void update(MemberUpdateRequest newMember, PasswordEncoder encoder) {	// 파라미터에 PasswordEncoder 추가
        this.password = newMember.password() == null || newMember.password().isBlank()
                ? this.password
                : encoder.encode(newMember.password());	// 비밀 번호 수정
        this.nickname = newMember.nickname();
        this.imageUrl = newMember.ImageUrl();
    }
}
