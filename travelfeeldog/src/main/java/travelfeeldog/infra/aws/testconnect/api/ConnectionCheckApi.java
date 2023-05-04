package travelfeeldog.infra.aws.testconnect.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travelfeeldog.global.common.dto.ApiResponse;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class ConnectionCheckApi {
    @GetMapping(value="/{testnumber}")
    public ApiResponse getConnectTest(@PathVariable Long testnumber){
        return ApiResponse.success(testnumber);
    }
}
