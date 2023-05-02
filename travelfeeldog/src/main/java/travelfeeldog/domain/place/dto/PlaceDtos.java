package travelfeeldog.domain.place.dto;

import java.util.List;
import javax.xml.transform.sax.SAXResult;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PlaceDtos {

    @Data
    @NoArgsConstructor
    public static class PlacePostRequestDto{
        private String name;
        private String describe;
        private String thumbnailImageUrl;
        private float latitude;
        private float longitude;
        private String categoryName;
        private String locationName;

    }
}
