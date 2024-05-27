package backend.taskweaver.domain.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "프로젝트 멤버 응답 DTO")
public record ProjectMemberResponse(
        List<MemberList> memberList
) {
    public record MemberList (
            @Schema(description = "멤버 ID", example = "1")
            Long id,
            @Schema(description = "이미지 URL", example = "https://taskweaverBucket.s3.ap-northeast-2.amazonaws.com/123.jpg")
            String imageUrl,
            @Schema(description = "멤버 닉네임", example = "윤채은")
            String nickname
    ) {
    }
}
