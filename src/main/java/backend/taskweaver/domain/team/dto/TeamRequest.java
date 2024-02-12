package backend.taskweaver.domain.team.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TeamRequest {

    @Getter
    public static class teamCreateRequest {
        @Schema(description = "팀 이름", example = "Team Name")
        String name;
    }
}
