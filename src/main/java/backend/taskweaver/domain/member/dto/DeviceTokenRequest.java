package backend.taskweaver.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record DeviceTokenRequest(
        @NotBlank
        @Schema(description = "기기의 디바이스 토큰", example = "d06CIzuIRIaayZ204Tflak:APA91bEFvl9mskawm2JDFSev1-PBcPMcsAKSGC7qnoKPNy7dGpm_cAWnYUcl2x5AR0SJcDof5jivJwIyrHGQs")
        String deviceToken
) {}
