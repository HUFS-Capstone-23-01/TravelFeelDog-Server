package travelfeeldog.global.auth.jwt.config;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import lombok.Getter;
@Getter
public class JwtSecretKey {

    private  String jwtSecretKey;
    private  Long jwtValidityAccessTime;
    private  Long jwtValidityRefreshTime;
    private final Key key;
    public JwtSecretKey(String jwtSecretKey, Long jwtValidityAccessTime, Long jwtValidityRefreshTime) {
        this.jwtSecretKey = jwtSecretKey;
        this.jwtValidityAccessTime = jwtValidityAccessTime;
        this.jwtValidityRefreshTime = jwtValidityRefreshTime;
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

}
