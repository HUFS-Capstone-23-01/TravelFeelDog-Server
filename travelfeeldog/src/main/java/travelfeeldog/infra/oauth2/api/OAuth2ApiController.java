package travelfeeldog.infra.oauth2.api;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@Slf4j
@RequestMapping("/login/oauth2/code")
public class OAuth2ApiController {
        @PostMapping("/mobile")
        public String mobileGoogleAuthenticationLogin(HttpServletResponse response) {
//            http://localhost:8080/oauth2/authorization/google
            log.info("!!!!!!!! This is api");
            for (String headerName : response.getHeaderNames()) {
                Collection<String> headerValues = response.getHeaders(headerName);
                for (String headerValue : headerValues) {
                    if(headerValue.equals("AccessToken")) return headerValue + " ";
                }
            }
            return "helloWorld";
        }
    @GetMapping("/google")
    public String googleAuthenticationLogin(
            @RequestParam String state,
            @RequestParam String code,
            @RequestParam String scope,
            @RequestParam(required = false) String authuser,
            @RequestParam(required = false) String prompt,
            HttpServletResponse response) {

        log.info("Received state: {}", state);
        log.info("Received code: {}", code);
        log.info("Received scope: {}", scope);
        if (authuser != null) log.info("Received authuser: {}", authuser);
        if (prompt != null) log.info("Received prompt: {}", prompt);

        // 여기서 state와 code를 이용하여 OAuth2 토큰을 발급받는 로직이 필요합니다.
        // OAuth2 공급자와의 통신을 위한 HttpClient를 사용하거나,
        // Spring Security OAuth2 클라이언트를 이용해 토큰을 얻어야 합니다.

        // 토큰을 얻은 후, 필요한 로직을 수행합니다. 예를 들어, 토큰을 이용해 사용자 정보를 불러온 후,
        // 사용자 계정을 생성하거나 업데이트하고, 자체 서비스 토큰을 발급할 수 있습니다.

        // 필요한 헤더를 추가합니다.
        response.addHeader("MyHeader", "MyValue");

        // TODO: 구현된 로직에 따라 적절한 응답을 반환합니다.
        return "Login Successful";
    }
}
