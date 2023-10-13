package travelfeeldog.global.auth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Enumeration;

@Slf4j
public class AuthorizationExtractor {
    public static final String AUTHORIZATION = "Authorization";
    private AuthorizationExtractor() {
    }

    public static String extract(HttpServletRequest request) {
        Enumeration<String> headers =request.getHeaders(AUTHORIZATION);
        while (headers.hasMoreElements()) {
            String token = headers.nextElement();
            log.info("Header Value: '{}'", token);
            return token;
        }
        return null;
    }
}
