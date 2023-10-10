package travelfeeldog.global.auth.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class JwtSecretKey {

    @Value("${security.jwt.secretKey}")
    private  String jwtSecretKey;
    @Value("${security.jwt.validityTime}")
    private  Long jwtValidityTime;

    private final Key key;
    public JwtSecretKey(){
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

}
