package travelfeeldog.global.auth.jwt.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    WRONG_TYPE_TOKEN("W001", "Wrong type of token."),
    EXPIRED_TOKEN("E001", "Token has expired."),
    UNSUPPORTED_TOKEN("U001", "Unsupported token."),
    ACCESS_DENIED("A001", "Access denied."),
    UNKNOWN_ERROR("U002", "Unknown error.");

    private final String code;
    private final String message;

    ExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}