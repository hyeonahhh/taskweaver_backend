package backend.taskweaver.global.code;

import lombok.Getter;

@Getter
public enum ErrorCode {

    /**
     * ******************************* Global Error CodeList ***************************************
     * HTTP Status Code
     * 400 : Bad Request
     * 401 : Unauthorized
     * 403 : Forbidden
     * 404 : Not Found
     * 500 : Internal Server Error
     * *********************************************************************************************
     */
    // 잘못된 서버 요청
    BAD_REQUEST_ERROR(400, "G001", "Bad Request Exception"),

    // @RequestBody 데이터 미 존재
    REQUEST_BODY_MISSING_ERROR(400, "G002", "Required request body is missing"),

    // 유효하지 않은 타입
    INVALID_TYPE_VALUE(400, "G003", " Invalid Type Value"),

    // Request Parameter 로 데이터가 전달되지 않을 경우
    MISSING_REQUEST_PARAMETER_ERROR(400, "G004", "Missing Servlet RequestParameter Exception"),

    // 입력/출력 값이 유효하지 않음
    IO_ERROR(400, "G005", "I/O Exception"),

    // com.google.gson JSON 파싱 실패
    JSON_PARSE_ERROR(400, "G006", "JsonParseException"),

    // com.fasterxml.jackson.core Processing Error
    JACKSON_PROCESS_ERROR(400, "G007", "com.fasterxml.jackson.core Exception"),

    // 권한이 없음
    FORBIDDEN_ERROR(403, "G008", "Forbidden Exception"),

    // 서버로 요청한 리소스가 존재하지 않음
    NOT_FOUND_ERROR(404, "G009", "Not Found Exception"),

    // NULL Point Exception 발생
    NULL_POINT_ERROR(404, "G010", "Null Point Exception"),

    // @RequestBody 및 @RequestParam, @PathVariable 값이 유효하지 않음
    NOT_VALID_ERROR(404, "G011", "handle Validation Exception"),

    // @RequestBody 및 @RequestParam, @PathVariable 값이 유효하지 않음
    NOT_VALID_HEADER_ERROR(404, "G012", "Header에 데이터가 존재하지 않는 경우 "),

    // 서버가 처리 할 방법을 모르는 경우 발생
    INTERNAL_SERVER_ERROR(500, "G999", "Internal Server Error Exception"),

    // 토큰 만료 기한이 지났을 때 발생
    EXPIRED_JWT_ERROR(404, "G013", "JWT Token expiration"),

    // 토큰 유효성 검사가 실패할 때 발생
    INVALID_JWT_ERROR(404, "G014", "Invalid JWT Token"),

    // 토큰 검사시 사용자 인증 실패할 때 발생
    USER_AUTH_ERROR(404, "G015", "User authentication failed"),


    /**
     * ******************************* Custom Error CodeList ***************************************
     */
    // Transaction Insert Error
    INSERT_ERROR(200, "9999", "Insert Transaction Error Exception"),

    // Transaction Update Error
    UPDATE_ERROR(200, "9999", "Update Transaction Error Exception"),

    // Transaction Delete Error
    DELETE_ERROR(200, "9999", "Delete Transaction Error Exception"),

    // PROJECT
    BELONG_TO_WRONG_TEAM_ERROR(400, "P001", "This Manager doesn't belong to this team"),
    PROJECT_NOT_FOUND(404, "T001", "Project Not Found"),
    NOT_PROJECT_MANAGER(403, "P004", "Only Project manager can do this work."),

    // TEAM
    TEAM_NOT_FOUND(404, "T001", "Team Not Found"),
    TEAM_MEMBER_NOT_FOUND(404, "T002", "Team member Not Found"),

    TEAM_MEMBER_STATE_NOT_FOUND(404, "T003", "No matching data in the team invitation status table."),
    INVITATION_ALREADY_SENT(404, "T004", "The invitation has already been sent."),
    NOT_TEAM_LEADER(404, "T005", " Not the leader of the team."),
    CANNOT_DELETE_TEAM_LEADER(404, "T006", "Team leader cannot be deleted"),


    //TASK
    TASK_STATE_NOT_FOUND(404, "TS001", "Task State Not Found"),

    // MEMBER
    DUPLICATED_EMAIL(409, "M001", "Email is duplicated"),
    MEMBER_NOT_FOUND(404, "M002", "Member Not Found"),


    ; // End

    /**
     * ******************************* Error Code Constructor ***************************************
     */
    // 에러 코드의 '코드 상태'을 반환한다.
    private final int status;

    // 에러 코드의 '코드간 구분 값'을 반환한다.
    private final String divisionCode;

    // 에러 코드의 '코드 메시지'을 반환한다.
    private final String message;

    // 생성자 구성
    ErrorCode(final int status, final String divisionCode, final String message) {
        this.status = status;
        this.divisionCode = divisionCode;
        this.message = message;
    }
}
