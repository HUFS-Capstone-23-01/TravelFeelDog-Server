package travelfeeldog.global.auth.secure;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import travelfeeldog.global.auth.jwt.JwtService;


@RequiredArgsConstructor
@Component
@Slf4j
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtService jwtService;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class);

    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        String token = AuthorizationExtractor.extract(Objects.requireNonNull(request));
        log.info("token {} ",token);
        if (token == null || token.trim().isEmpty()) {
            return null;
        }
        return jwtService.findEmailByToken(token);
    }
}