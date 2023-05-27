package travelfeeldog.global.common.error;

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
        return ApiResponse.error(500,"서버 로직 에러:" + e);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse MissingRequestHeaderException(Exception e) {
        e.printStackTrace();
        return ApiResponse.error(400,"MissingRequestHeaderException");
    }
    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse SignatureException(Exception e) {
        e.printStackTrace();
        return ApiResponse.error( 404,"SignatureException");
    }
    @ExceptionHandler(ServiceUnavailableException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse ServiceUnavailableExpection(Exception e) {
        e.printStackTrace();
        return ApiResponse.error(503,"Service Unavailable");
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse MssingServletRequestPartException(Exception e) {
        e.printStackTrace();
        return ApiResponse.error(405,"파일 형식 혹은 파일 파라미터의 이름 오류 혹은 파일 없음");
    }

    @ExceptionHandler(NoResultException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse NoContentExpection(Exception e) {
       e.printStackTrace();
       return ApiResponse.error(400,"No results content" );
    }
    //    @ExceptionHandler(UnsupportedJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse UnsupportedJwtException(Exception e) {
        e.printStackTrace();
        return ApiResponse.error( 401,"UnsupportedJwtException");
    }

    //    @ExceptionHandler(MalformedJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse MalformedJwtException(Exception e) {
        e.printStackTrace();
        return ApiResponse.error( 402,"MalformedJwtException");
    }

    //    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse ExpiredJwtException(Exception e) {
        e.printStackTrace();
        return ApiResponse.error( 403,"ExpiredJwtException");
    }

}
