//package travelfeeldog.infra.redis;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class RedisService {
//
//    private final RedisTemplate<String, String> redisTemplate;
//    private static final String TRACKING = "tracking:";
//    private static final long TRACKING_USER_EXPIRE_TIME = 15 * 1000L;    // 15초
//
//    public boolean checkDuplicateLogins(String email) {
//        return redisTemplate.opsForValue().get(TRACKING + email) != null;
//    }
//
//    public void saveTrackingUserSession(String email, String token) {
//        if (email.equals("ssafyezpz@gmail.com")) return; //관리자 권한은 중복 허용
//
//        // 토큰 비교해서 같은 이용자는 유효시간 갱신
//        // 토큰 날아가면 갱신 불가!
//        if (!checkDuplicateLogins(email) || token.equals(redisTemplate.opsForValue().get(TRACKING + email)))
//            redisTemplate.opsForValue().set(TRACKING + email, token, TRACKING_USER_EXPIRE_TIME, TimeUnit.MILLISECONDS);
//        else
//            log.info("현재 이용할 수 없습니다.");
//    }
//
//    public void removeTrackingUserSession(String email, String token) {
//        if (token.equals(redisTemplate.opsForValue().get(TRACKING + email)))
//            redisTemplate.delete(TRACKING + email);
//    }
//}