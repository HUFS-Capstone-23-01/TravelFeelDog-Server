package travelfeeldog.global.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import travelfeeldog.member.domain.application.service.MemberService;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService {
    private final MemberService memberService;

    public UserDetails loadUserByUsername(String s) {
        return null;
    }
}
