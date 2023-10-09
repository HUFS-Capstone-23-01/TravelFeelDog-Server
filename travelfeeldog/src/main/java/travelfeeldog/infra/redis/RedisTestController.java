package travelfeeldog.infra.redis;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("redis")
public class RedisTestController {

    private final RedisService redisSampleService;

    public RedisTestController(RedisService redisSampleService) {
        this.redisSampleService = redisSampleService;
    }

    @PostMapping(value = "/getRedisStringValue")
    public void getRedisStringValue(String key) {
        redisSampleService.getRedisStringValue(key);
    }
}
