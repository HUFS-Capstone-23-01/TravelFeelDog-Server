package travelfeeldog.global.auth.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final JwtProvider jwtProvider;
    public String findEmailByToken(String token) {
        return jwtProvider.extractEmail(token);
    }
}
