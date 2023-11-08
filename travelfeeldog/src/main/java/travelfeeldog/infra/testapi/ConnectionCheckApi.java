package travelfeeldog.infra.testapi;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
//@Slf4j
public class ConnectionCheckApi {

    @GetMapping(value = "/test")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> getConnectUserTestNumber(@RequestParam Long testNumber,
                                                         @RequestHeader("Authorization") String token) {
//        log.info("email : {}",email);
        return ResponseEntity.ok(testNumber);
    }
}
