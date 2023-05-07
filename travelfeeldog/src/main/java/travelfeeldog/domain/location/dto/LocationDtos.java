package travelfeeldog.domain.location.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

public class LocationDtos {

    @Data
    @NoArgsConstructor
    public static class RequestLocationDto{
        private String name;
    }
}
