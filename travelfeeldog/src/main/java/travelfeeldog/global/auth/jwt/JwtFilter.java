package travelfeeldog.global.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.filter.OncePerRequestFilter;
import travelfeeldog.member.domain.model.Member;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final String[] PERMIT_URL_ARRAY = {
            "/",
            "/login",
            "/app/index.js",
            "/favicon.ico",
            "/oauth2/authorization/google",
            "/login/oauth2/code/google",
            "api/v1/member/register"
    };
    private final static String AUTHORIZATION_HEADER = "Authorization";
    private final JwtService jwtService;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    public boolean isIgnoreURL(String uri) {
        for (String item : PERMIT_URL_ARRAY) {
            if (uri.equals(item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        if (isIgnoreURL(request.getRequestURI())) {
            filterChain.doFilter(request, response); // "/login" 요청이 들어오면, 다음 필터 호출
            return;
        }
        final String token = request.getHeader(AUTHORIZATION_HEADER);
        Member member = jwtService.findMemberByToken(token);
        saveAuthentication(member);
        filterChain.doFilter(request, response);

    }

    public void saveAuthentication(Member myUser) {
        String password = myUser.getEmail() + myUser.getNickName();
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