package travelfeeldog.infra.oauth2.service;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import travelfeeldog.infra.oauth2.dto.OAuthAttributes;
import travelfeeldog.member.domain.application.service.MemberWriteService;
import travelfeeldog.member.domain.model.Member;

@Service
@RequiredArgsConstructor
public class GoogleLoginService {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    String clientId;
    private final GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
            .setAudience(Collections.singletonList(clientId))
            .build();
    private final MemberWriteService memberWrite;
    public String loginGoogleOAuthWithIdToken(String idToken) {
        OAuthAttributes attributes = verifyGoogleIDToken(idToken);
        Member member = memberWrite.saveByAttributes(attributes);
        memberWrite.save(member);
        return attributes.getEmail();
    }
    public OAuthAttributes verifyGoogleIDToken(String idToken) { // 최 초 진입 회원 가입 갈라야함
        try {
            GoogleIdToken idTokenObj = verifier.verify(idToken);
            if (idTokenObj == null) {
                return null;
            }
            Payload payload = idTokenObj.getPayload();
            OAuthAttributes attributes = OAuthAttributes.ofGoogle("sub",payload);
            return attributes;
        } catch (GeneralSecurityException | IOException e) {
            throw new IllegalArgumentException("Wrong In IdToken"); // new custom exception
        }
    }

}
