package travelfeeldog.domain.place.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;
import travelfeeldog.domain.place.model.Place;


public class PlaceDtos {

    @Data
    @NoArgsConstructor
    public static class PlacePostRequestDto{
        private String name;
        private String describe;
        private String address;
        private float latitude;
        private float longitude;
        private String locationName;
        private String categoryName;
        private List<String> facilityNames;
    }
    @Data
    @NoArgsConstructor
    public static class PlaceDetailDto{
        private String name;
        private String describe;
        private String thumbNailImageUrl;
        private String address;
        private float latitude;
        private float longitude;
        private List<String> facilityNames;
        public PlaceDetailDto(Place place){
            this.name =place.getName();
            this.name = place.getName();
            this.describe = place.getDescribe();
            this.thumbNailImageUrl = place.getThumbNailImageUrl();
            this.address = place.getAddress();
            this.latitude = place.getLatitude();
            this.longitude = place.getLongitude();
            this.facilityNames = place.getPlaceFacilities().stream().map(pf -> pf.getFacility().getName())
                    .collect(Collectors.toList());
        }
    }
}
