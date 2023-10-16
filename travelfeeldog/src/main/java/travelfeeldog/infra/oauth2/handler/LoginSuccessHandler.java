package travelfeeldog.infra.oauth2.handler;

import java.util.Map;
import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.Authentication;
import org.springframework.web.util.UriComponentsBuilder;

import travelfeeldog.global.auth.jwt.service.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String REDIRECT_URL = "/";
    private final JwtService jwtService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        String email = extractUsername(authentication);
        jwtService.tokenUpdateCheck(email);
        getRedirectStrategy().sendRedirect(request, response, getRedirectUrl(email));
    }

    private String extractUsername(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        return (String) attributes.get("email");
    }
    private String getRedirectUrl(String email) {
        return UriComponentsBuilder.fromUriString(REDIRECT_URL)
                .queryParam("Access", jwtService.getAccessTokenByEmail(email))
                .queryParam("Refresh", jwtService.getRefreshTokenByEmail(email))
                .build().toUriString();
    }
}
