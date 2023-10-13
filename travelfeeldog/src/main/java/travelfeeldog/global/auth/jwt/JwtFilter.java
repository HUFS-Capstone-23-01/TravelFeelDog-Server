package travelfeeldog.global.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import travelfeeldog.member.domain.model.Member;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final static String AUTHORIZATION_HEADER = "Authorization";
    private final static String NO_CHECK_HOME_URL = "/";
    private final static String  NO_CHECK_REGISTER_URL = "api/v1/member/register";
    private final static String NO_CHECK_URL_Login_URL = "/oauth2/authorization/google";
    private final static String NO_CHECK_URL_RE_URL = "/login/oauth2/code/google";

    private final JwtProvider tokenProvider;
    private final JwtService jwtService;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("request dofilter : {}",request.getRequestURI());
        if (request.getRequestURI().equals(NO_CHECK_HOME_URL) || request.getRequestURI().contains(NO_CHECK_URL_Login_URL)
        || request.getRequestURI().contains(NO_CHECK_URL_RE_URL) || request.getRequestURI().contains(NO_CHECK_REGISTER_URL)
        ) {
            filterChain.doFilter(request, response); // "/login" 요청이 들어오면, 다음 필터 호출
            return;
        }
        final String token = request.getHeader(AUTHORIZATION_HEADER);

        log.info("request authorizationHeader : {}",token);
        log.info("claims: {}",tokenProvider.extractClaims(token)); // claims: {sub=jo187712@gmail.com, iat=1697164996, exp=1697200996}
        Member member = jwtService.findMemberByToken(token);
        saveAuthentication(member); // 없으면 구글 로그인 리다이렉션 됨 팔수임
        filterChain.doFilter(request, response);

    }
    public void saveAuthentication(Member myUser) {
        String password = myUser.getEmail() + myUser.getNickName();

        // 소셜 로그인 유저의 비밀번호 임의로 설정 하여 소셜 로그인 유저도 인증 되도록 설정

        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(myUser.getEmail())
                .password(password)
                .roles(myUser.getRole().name())
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                        authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}