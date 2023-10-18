package travelfeeldog.infra.oauth2.api;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travelfeeldog.global.auth.jwt.service.JwtService;
import travelfeeldog.global.common.dto.ApiResponse;
import travelfeeldog.infra.oauth2.service.GoogleLoginService;
import travelfeeldog.member.domain.model.Member;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class OAuth2ApiController {
    private final GoogleLoginService googleLoginService;
    private final JwtService jwtService;
    @PostMapping("/oauth2/token")
    public ApiResponse<TokenLoginResponse> googleAuthenticationLogin(@RequestBody TokenLoginRequest request) {
        Member member = jwtService.findMemberByToken(request.token());
        return ApiResponse.success(jwtService.getTokenLoginResponseByMember(member));
    }

    @PostMapping("/oauth2/google/token")
    public ApiResponse<TokenLoginResponse> mobileGoogleAuthenticationLogin(@RequestBody TokenLoginRequest request) {
        Member member = googleLoginService.loginGoogleOAuthWithIdToken(request.token());
        return ApiResponse.success(jwtService.getTokenLoginResponseByMember(member));
    }

}
