package travelfeeldog.infra.oauth2.api;

import java.io.IOException;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    // mobile 과 web 에서 스프링에서 발급한 jwt 을 통한 회원 정보 호출
    @PostMapping("/oauth2/token")
    public ApiResponse<TokenLoginResponse> googleAuthenticationLogin(
            @RequestBody TokenLoginRequest request) {
        Member member = jwtService.findMemberByToken(request.token());
        return ApiResponse.success(jwtService.getTokenLoginResponseByMember(member));
    }

    // 웹 에서 구글 ID 토큰을 통한 회원 정보 호출  :  회원 이메일 타입, jwt
    @PostMapping("/web/oauth2/google/idToken")
    public ApiResponse<TokenLoginResponse> webGoogleAuthenticationLogin(
            @RequestBody TokenLoginRequest request) {
        Member member = googleLoginService.loginGoogleOAuthWithWebIdToken(request.token());
        return ApiResponse.success(jwtService.getTokenLoginResponseByMember(member));
    }

    // mobile 에서 구글 ID 토큰을 통한 회원 정보 호출  :  회원 이메일 타입, jwt
    @PostMapping("/mobile/oauth2/google/idToken")
    public ApiResponse<TokenLoginResponse> mobileGoogleAuthenticationLogin(@RequestBody TokenLoginRequest request) {
        Member member = googleLoginService.loginGoogleOAuthWithMobileIdToken(request.token());
        return ApiResponse.success(jwtService.getTokenLoginResponseByMember(member));
    }

    // Spring , React 에서 IO 토큰 발급을 위한 Enrty Point , 접속시 구글 로그인 창으로 이동
    // http://localhost:8080/login/web/oauth2/google
    @GetMapping("/web/oauth2/google")
    public ResponseEntity<Void> redirectToGoogleLogin() {
        String googleLoginUrl = googleLoginService.redirectToGoogleLogin();
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(googleLoginUrl)).build();
    }

    // 구글 로그인 이후 ID 토큰을 발급받는  redirection url
    @GetMapping("/web/oauth2/code/google")
    public ResponseEntity<String> handleGoogleLoginCallback(
            @RequestParam String code,
            @RequestParam String state) throws IOException {
        String token = googleLoginService.getIdTokenFromGoogle(code);

//        String token = googleLoginService.getAccessTokenFromGoogle(code);
        return ResponseEntity.ok(token);
    }

}
