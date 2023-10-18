package travelfeeldog.global.common.dto;

import org.springframework.http.HttpStatus;

public record ApiResponse<T>(String message, T data) {

    private static final String SUCCESS_MESSAGE = "SUCCESS";
    private static final String NOT_FOUND_MESSAGE = "NOT FOUND";
    private static final String FAILED_MESSAGE = "서버에서 오류가 발생하였습니다.";
    private static final String INVALID_ACCESS_TOKEN = "Invalid access token.";
    private static final String INVALID_REFRESH_TOKEN = "Invalid refresh token.";
    private static final String NOT_EXPIRED_TOKEN_YET = "Not expired token yet.";
    private static final String NAME_REDUNDANT = "중복된 닉네임입니다. 다른 닉네임으로 시도해주세요.";
    private static final String TOKEN_BLANK = "토큰이 존재하지 않습니다.";

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T body) {
        return new ApiResponse<T>(SUCCESS_MESSAGE, body);
    }

    public static <T> ApiResponse<T> success(HttpStatus httpStatus) {
        return new ApiResponse<>(httpStatus.getReasonPhrase(), null);
    }

    public static <T> ApiResponse<T> success(HttpStatus httpStatus, T body) {
        return new ApiResponse<>(httpStatus.getReasonPhrase(), body);
    }

    public static <T> ApiResponse<T> error(String message, T body) {
        return new ApiResponse<>(message, body);
    }

    public static <T> ApiResponse<T> error(HttpStatus httpStatus) {
        return new ApiResponse<>(httpStatus.getReasonPhrase(), null);
    }

    public static <T> ApiResponse<T> fail(T body) {
        return new ApiResponse<>(FAILED_MESSAGE, body);
    }

    public static <T> ApiResponse<T> invalidAccessToken(T body) {
        return new ApiResponse<T>(INVALID_ACCESS_TOKEN, body);
    }

    public static <T> ApiResponse<T> invalidRefreshToken() {
        return new ApiResponse<>(INVALID_REFRESH_TOKEN, null);
    }

    public static <T> ApiResponse<T> notExpiredTokenYet() {
        return new ApiResponse<>(NOT_EXPIRED_TOKEN_YET, null);
    }

    public static <T> ApiResponse<T> redundantName(T body) {
        return new ApiResponse<>(NAME_REDUNDANT, body);
    }
}
