package travelfeeldog.global.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private final static int SUCCESS = 200;
    private final static int NOT_FOUND = 400;
    private final static int FAILED = 500;
    private final static String SUCCESS_MESSAGE = "SUCCESS";
    private final static String NOT_FOUND_MESSAGE = "NOT FOUND";
    private final static String FAILED_MESSAGE = "서버에서 오류가 발생하였습니다.";
    private final static String INVALID_ACCESS_TOKEN = "Invalid access token.";
    private final static String INVALID_REFRESH_TOKEN = "Invalid refresh token.";
    private final static String NOT_EXPIRED_TOKEN_YET = "Not expired token yet.";
    private final static String NAME_REDUNDANT = "중복된 닉네임입니다. 다른 닉네임으로 시도해주세요.";
    private final static String TOKEN_BLANK = "토큰이 존재하지 않습니다.";

    private final ApiResponseHeader header;

    private final T body;

    public static <T> ApiResponse<T> success(T body) {
        return new ApiResponse(new ApiResponseHeader(SUCCESS, SUCCESS_MESSAGE),body);
    }
    public static <T> ApiResponse<T> success(HttpStatus httpStatus) {
        return new ApiResponse(new ApiResponseHeader(httpStatus.value(), httpStatus.getReasonPhrase()), null);
    }
    public static <T> ApiResponse<T> success(HttpStatus httpStatus,T body) {
        return new ApiResponse(new ApiResponseHeader(httpStatus.value(), httpStatus.getReasonPhrase()), body);
    }
    public static <T> ApiResponse<T> error(int code , String message) {
        return new ApiResponse(new ApiResponseHeader(code, message),null);
    }
    public static <T> ApiResponse<T> error(HttpStatus httpStatus) {
        return new ApiResponse(new ApiResponseHeader(httpStatus.value(), httpStatus.getReasonPhrase()), null);
    }
    public static <T> ApiResponse<T> fail(T body) {
        return new ApiResponse(new ApiResponseHeader(FAILED, FAILED_MESSAGE), body);
    }

    public static <T> ApiResponse<T> invalidAccessToken() {
        return new ApiResponse(new ApiResponseHeader(FAILED, INVALID_ACCESS_TOKEN), null);
    }

    public static <T> ApiResponse<T> invalidRefreshToken() {
        return new ApiResponse(new ApiResponseHeader(FAILED, INVALID_REFRESH_TOKEN), null);
    }

    public static <T> ApiResponse<T> notExpiredTokenYet() {
        return new ApiResponse(new ApiResponseHeader(FAILED, NOT_EXPIRED_TOKEN_YET), null);
    }

    public static <T> ApiResponse<T> redundantName(T body) {
        return new ApiResponse(new ApiResponseHeader(SUCCESS, NAME_REDUNDANT), body);
    }

    public static <T> ApiResponse<T> invaildToken(T body) {
        return new ApiResponse(new ApiResponseHeader(FAILED, TOKEN_BLANK), body);
    }
}
