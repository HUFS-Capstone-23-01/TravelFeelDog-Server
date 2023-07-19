package travelfeeldog.global.common.error;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.web.bind.MissingPathVariableException;
import travelfeeldog.global.common.dto.ApiResponse;
import java.security.SignatureException;
import javax.naming.ServiceUnavailableException;
import javax.persistence.NoResultException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse ServerException2(Exception e) {
        e.printStackTrace();
        return ApiResponse.error("서버 로직 에러:" + e); // 500
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse MissingRequestHeaderException(Exception e) {
        e.printStackTrace();
        return ApiResponse.error("MissingRequestHeaderException"); // 400
    }
    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse MissingPathVariableException(Exception e) {
        e.printStackTrace();
        return ApiResponse.error("잘못된 경로 입니다."); // 404
    }
    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse SignatureException(Exception e) {
        e.printStackTrace();
        return ApiResponse.error("SignatureException"); //404
    }
    @ExceptionHandler(ServiceUnavailableException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse ServiceUnavailableExpection(Exception e) {
        e.printStackTrace();
        return ApiResponse.error("Service Unavailable");  //503
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse MssingServletRequestPartException(Exception e) {
        e.printStackTrace();
        return ApiResponse.error("파일 형식 혹은 파일 파라미터의 이름 오류 혹은 파일 없음"); // 405
    }

    @ExceptionHandler(NoResultException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse NoContentExpection(Exception e) {
       e.printStackTrace();
       return ApiResponse.error("No results content" ); // 400
    }
    @ExceptionHandler(UnsupportedJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse UnsupportedJwtException(Exception e) {
        e.printStackTrace();
        return ApiResponse.error("UnsupportedJwtException"); // 401
    }

    @ExceptionHandler(MalformedJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse MalformedJwtException(Exception e) {
        e.printStackTrace();
        return ApiResponse.error("MalformedJwtException");//402
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse ExpiredJwtException(Exception e) {
        e.printStackTrace();
        return ApiResponse.error("ExpiredJwtException"); // 403
    }

}
