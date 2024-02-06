package backend.taskweaver.global.exception.handler;

import backend.taskweaver.global.code.ErrorCode;
import lombok.Builder;

public class JwtTokenHandler extends RuntimeException {

    private final ErrorCode errorCode;

    @Builder
    public JwtTokenHandler(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    @Builder
    public JwtTokenHandler(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
