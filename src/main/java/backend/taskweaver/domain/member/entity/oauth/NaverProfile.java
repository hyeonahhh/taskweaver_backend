package backend.taskweaver.domain.member.entity.oauth;

import lombok.Data;

@Data
public class NaverProfile {
    private int resultcode;
    private String message;
    private NaverResponse response;

    @Data
    public class NaverResponse {
        public String id;
        public String email;
    }
}