package travelfeeldog.global.auth.jwt.filter;

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

import org.springframework.web.filter.OncePerRequestFilter;
import travelfeeldog.global.auth.jwt.exception.ExceptionCode;
import travelfeeldog.global.auth.jwt.exception.InvalidTokenException;
import travelfeeldog.global.auth.jwt.service.JwtService;
import travelfeeldog.member.domain.model.Member;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final static String AUTHORIZATION_HEADER = "Authorization";
    private final JwtService jwtService;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().contains("/api/v1")) {
            final String token = request.getHeader(AUTHORIZATION_HEADER);
            try{

                jwtService.validateToken(token);
                Member member = jwtService.findMemberByToken(token);
                saveAuthentication(member);
            }catch (InvalidTokenException e){
                request.setAttribute("exception", ExceptionCode.EXPIRED_TOKEN.getCode());
            }
        }
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