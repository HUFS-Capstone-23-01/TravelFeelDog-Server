package travelfeeldog.infra.api;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import travelfeeldog.global.auth.LoginUser;

@RestController
@RequestMapping("/api/v1/test")
@AllArgsConstructor
@Slf4j
public class ConnectionCheckApi {
    @GetMapping(value="/{testNumber}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> getConnectTestNumber(@PathVariable Long testNumber , @LoginUser String email){
        log.info("email{}",email);
        return ResponseEntity.ok(testNumber);
    }
}
