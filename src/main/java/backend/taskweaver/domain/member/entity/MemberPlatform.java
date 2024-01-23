package backend.taskweaver.domain.member.entity;//package backend.taskweaver.domain.member.entity;
//
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
//@Getter
//@Entity
//@Table(name = "member_platform")
//public class MemberPlatform {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "member_platform_id")
//    private Long id;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "provider", nullable = false)
//    private Provider provider;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//}
