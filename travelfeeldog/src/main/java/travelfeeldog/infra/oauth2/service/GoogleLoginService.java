package travelfeeldog.infra.oauth2.service;


import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import travelfeeldog.infra.oauth2.dto.OAuthAttributes;
import travelfeeldog.member.domain.application.service.MemberWriteService;
import travelfeeldog.member.domain.model.Member;

@Service
@Slf4j
public class GoogleLoginService {

    private final String mobileClientId;
    private GoogleIdTokenVerifier verifier;
    private final MemberWriteService memberWrite;
    private final String webClientId;
    private final String webClientSecret;
    private final String redirectWebUrl;
    private static final List<String> SCOPES = Arrays.asList(
            "https://www.googleapis.com/auth/userinfo.email",
            "https://www.googleapis.com/auth/userinfo.profile"
    );

    @Autowired
    public GoogleLoginService(
            @Value("${spring.security.oauth2.client.registration.google-mobile.client-id}") String mobileClientId,
            @Value("${spring.security.oauth2.client.registration.google-web-react.client-id}") String webClientId,
            @Value("${spring.security.oauth2.client.registration.google-web-react.client-secret}") String webClientSecret,
            @Value("${spring.security.oauth2.client.registration.google-web-react.redirect-uri}") String redirectWebUrl,
            MemberWriteService memberWrite) {
        this.mobileClientId = mobileClientId;
        this.webClientId = webClientId;
        this.webClientSecret = webClientSecret;
        this.memberWrite = memberWrite;
        this.redirectWebUrl = redirectWebUrl;
    }

    public Member loginGoogleOAuthWithMobileIdToken(String idToken) {
        setVerifier(this.mobileClientId);
        return saveMemberByAttributes(idToken);
    }

    public Member loginGoogleOAuthWithWebIdToken(String idToken) {
        setVerifier(this.webClientId);
        return saveMemberByAttributes(idToken);
    }

    private void setVerifier(String clientId) {
        this.verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(clientId))
                .build();
    }

    private Member saveMemberByAttributes(String idToken) {
        OAuthAttributes attributes = verifyGoogleIDToken(idToken);
        return memberWrite.saveByAttributes(attributes);
    }

    public OAuthAttributes verifyGoogleIDToken(String idToken) {
        GoogleIdToken idTokenObj;
        try {
            idTokenObj = verifier.verify(idToken);
        } catch (GeneralSecurityException | IOException e) {
            log.info("[ERROR] : ", e);
            throw new IllegalArgumentException("Wrong In verify IdToken", e);
        }
        Payload payload = idTokenObj.getPayload();
        OAuthAttributes attributes = OAuthAttributes.ofGoogle("sub", payload);
        if (attributes == null) {
            log.info("[ERROR] : no Attributes");
            throw new IllegalArgumentException("Wrong In IdToken payload");
        }
        return attributes;
    }

    public String redirectToGoogleLogin() {
        return buildGoogleAuthorizationCodeFlow()
                .newAuthorizationUrl()
                .setRedirectUri(this.redirectWebUrl)
                .setState("state_parameter_passthrough_value")
                .build();
    }

    public String getIdTokenFromGoogle(String code) throws IOException {
        return getTokenResponseFromGoogle(code).getIdToken();
    }

    public String getAccessTokenFromGoogle(String code) throws IOException {
        return getTokenResponseFromGoogle(code).getAccessToken();
    }

    private GoogleTokenResponse getTokenResponseFromGoogle(String code) throws IOException {
        return buildGoogleAuthorizationCodeFlow()
                .newTokenRequest(code)
                .setRedirectUri(redirectWebUrl)
                .execute();
    }

    private GoogleAuthorizationCodeFlow buildGoogleAuthorizationCodeFlow() {
        return new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
                webClientId, webClientSecret, SCOPES)
                .setAccessType("offline")
                .setApprovalPrompt("force")
                .build();
    }
}
