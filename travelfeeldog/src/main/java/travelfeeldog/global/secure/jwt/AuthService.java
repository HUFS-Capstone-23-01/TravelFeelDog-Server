//package travelfeeldog.global.secure.jwt;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Transactional(readOnly=true)
//@Service
//@RequiredArgsConstructor
//public class AuthService {
//    public static String createToken(String u_nickname)  {
//        String token = JwtProvider.createToken(u_nickname); //토큰 생성
//        Claims claims = JwtProvider.parseJwtToken("Bearer "+ token); //토큰 검증
//
//        TokenDataResponse tokenDataResponse = new TokenDataResponse(token, claims.getSubject(), claims.getIssuedAt().toString(), claims.getExpiration().toString());
//        return tokenDataResponse.getToken();
//    }
//
//    public TokenResponseNoData checkToken(String token) throws Exception {
//        Claims claims = JwtProvider.parseJwtToken(token);
//
//        return  new TokenResponseNoData("200", "success");
//    }
//}
