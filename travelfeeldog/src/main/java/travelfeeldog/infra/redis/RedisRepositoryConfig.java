//package travelfeeldog.infra.redis;
//
//@RequiredArgsConstructor
//@Configuration
//@EnableRedisRepositories
//public class RedisRepositoryConfig {
//    private final RedisProperties redisProperties;
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory());
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
//        return redisTemplate;
//    }
//}