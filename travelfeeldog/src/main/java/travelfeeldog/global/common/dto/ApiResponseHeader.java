package travelfeeldog.global.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ApiResponseHeader {
    private int status;
    private String message;
}
