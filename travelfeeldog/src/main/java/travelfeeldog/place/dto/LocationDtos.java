package travelfeeldog.place.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import travelfeeldog.place.domain.location.model.Location;

public class LocationDtos {

    @Data
    @NoArgsConstructor
    public static class RequestLocationDto{
        private String name;
    }
    @Data
    @NoArgsConstructor
    public static class ResponseLocationDto{
        private Long id;
        private String name;
        public ResponseLocationDto(Location location){
            this.id =location.getId();
            this.name = location.getName();
        }
    }
}
