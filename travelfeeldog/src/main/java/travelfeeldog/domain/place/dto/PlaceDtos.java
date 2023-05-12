package travelfeeldog.domain.place.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;
import travelfeeldog.domain.place.model.Place;
import travelfeeldog.domain.place.model.PlaceStatic;
import travelfeeldog.domain.review.dto.ReviewDtos.SingleDescriptionAndNickNameDto;


public class PlaceDtos {
    @Data
    @NoArgsConstructor
    public static class PlaceSearchResponseDetailDto<T>{
        public T data;
        public List<String> goodKeyWords;
        public PlaceSearchResponseDetailDto(T data,List<String >request){
            this.data = data;
            this.goodKeyWords =request;
        }
    }
    @Data
    @NoArgsConstructor
    public static class PlaceResponseRecommendDetailDto{
        private Long id;
        private String name;
        private String thumbNailImageUrl;
        public PlaceResponseRecommendDetailDto(Place place){
            this.id =place.getId();
            this.name = place.getName();
            this.thumbNailImageUrl = place.getThumbNailImageUrl();
        }
    }
    @Data
    public static class PlaceReviewCountSortResponseDto{
        private Long id ;
        private String name;
        private String thumbNailImageUrl;
        private List<SingleDescriptionAndNickNameDto> reviews;
        public PlaceReviewCountSortResponseDto(Place place){
            this.id = place.getId();
            this.name = place.getName();
            this.thumbNailImageUrl = place.getThumbNailImageUrl();
            this.reviews = place.getReviews().stream().map(SingleDescriptionAndNickNameDto::new).toList();
        }
    }

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
        private Long id;
        private String name;
        private String describe;
        private String thumbNailImageUrl;
        private String address;
        private float latitude;
        private float longitude;
        private List<String> facilityNames;
        public PlaceDetailDto(Place place){
            this.id=place.getId();
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
    @Data
    @NoArgsConstructor
    public static class PlaceResponseDetailDto{
        private Long id;
        private String name;
        private String describe;
        private int reviewCount;
        private String thumbNailImageUrl;
        private List<String> facilityNames;
        private String address;
        private float latitude;
        private float longitude;
        private int reviewCountBad;
        private int reviewCountGood;
        private int reviewCountIdk;
        private int smallDogGoodTotal;
        private int smallDogBadTotal;
        private int mediumDogGoodTotal;
        private int mediumDogBadTotal;
        private int largeDogGoodTotal;
        private int largeDogBadTotal;
        public PlaceResponseDetailDto(Place place, PlaceStatic placeStatic){
            this.id = place.getId();
            this.name =place.getName();
            this.name = place.getName();
            this.describe = place.getDescribe();
            this.reviewCount = place.getReviewCount();
            this.thumbNailImageUrl = place.getThumbNailImageUrl();
            this.address = place.getAddress();
            this.latitude = place.getLatitude();
            this.longitude = place.getLongitude();
            this.facilityNames = place.getPlaceFacilities().stream().map(pf -> pf.getFacility().getName())
                    .collect(Collectors.toList());
            this.reviewCountGood = placeStatic.getReviewCountGood();
            this.reviewCountBad = placeStatic.getReviewCountBad();
            this.reviewCountIdk = placeStatic.getReviewCountIdk();
            this.smallDogGoodTotal = placeStatic.getSmallDogGoodTotal();
            this.smallDogBadTotal = placeStatic.getSmallDogBadTotal();
            this.mediumDogGoodTotal = placeStatic.getMediumDogGoodTotal();
            this.mediumDogBadTotal = placeStatic.getMediumDogBadTotal();
            this.largeDogGoodTotal = placeStatic.getLargeDogGoodTotal();
            this.largeDogBadTotal = placeStatic.getLargeDogBadTotal();


        }
    }
}
