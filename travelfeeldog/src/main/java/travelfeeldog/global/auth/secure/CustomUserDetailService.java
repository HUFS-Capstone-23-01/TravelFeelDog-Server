package travelfeeldog.global.auth.secure;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import travelfeeldog.member.application.service.MemberService;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService {
    private final MemberService memberService;

    public UserDetails loadUserByUsername(String s) {
        return null;
    }
}
