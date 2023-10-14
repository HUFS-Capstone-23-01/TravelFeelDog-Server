package travelfeeldog.global.auth.jwt.config;

import org.json.simple.JSONObject;
import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import travelfeeldog.global.auth.jwt.exception.ExceptionCode;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint  implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String exception = (String)request.getAttribute("exception");
        if(exception == null) {
        }
        //잘못된 타입의 토큰인 경우
        else if(exception.equals(ExceptionCode.WRONG_TYPE_TOKEN.getCode())) {
            setResponse(response, ExceptionCode.WRONG_TYPE_TOKEN);
        }
        //토큰 만료된 경우
        else if(exception.equals(ExceptionCode.EXPIRED_TOKEN.getCode())) {
            setResponse(response, ExceptionCode.EXPIRED_TOKEN);
        }
        //지원되지 않는 토큰인 경우
        else if(exception.equals(ExceptionCode.UNSUPPORTED_TOKEN.getCode())) {
            setResponse(response, ExceptionCode.UNSUPPORTED_TOKEN);
        }
        else {
            setResponse(response, ExceptionCode.ACCESS_DENIED);
        }
    }
    private void setResponse(HttpServletResponse response, ExceptionCode exceptionCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject responseJson = new JSONObject();
        responseJson.put("message", exceptionCode.getMessage());
        responseJson.put("code", exceptionCode.getCode());

        response.getWriter().print(responseJson);
    }
}
