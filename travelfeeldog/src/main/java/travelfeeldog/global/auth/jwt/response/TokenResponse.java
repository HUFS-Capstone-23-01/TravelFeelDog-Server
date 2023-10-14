package travelfeeldog.global.auth.jwt.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TokenResponse {
    private final String accessToken;
    private final String refreshToken;

    public TokenResponse(TokenResponse newToken) {
        this.accessToken = newToken.getAccessToken();
        this.refreshToken = newToken.getRefreshToken();
    }
}
