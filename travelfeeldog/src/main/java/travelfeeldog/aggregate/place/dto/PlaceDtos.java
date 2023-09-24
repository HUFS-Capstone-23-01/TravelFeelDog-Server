package travelfeeldog.aggregate.place.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import travelfeeldog.aggregate.place.domain.place.model.Place;
import travelfeeldog.aggregate.place.domain.place.model.PlaceStatistic;
import travelfeeldog.aggregate.review.dto.ReviewDtos.SingleDescriptionAndNickNameDto;


public class PlaceDtos {
    @Data
    @NoArgsConstructor
    public static class PlaceSearchResponseDto{
        private Long id;
        private String thumbNailImageUrl;
        private String placeName;
        private String categoryName;
        private String address;
        private int likes;
        public List<String> goodKeyWords;
        public PlaceSearchResponseDto(Place place) {
            this.id = place.getId();
            this.thumbNailImageUrl = place.getThumbNailImageUrl();
            this.placeName = place.getName();
            this.categoryName = place.getCategory().getName();
            this.address = place.getAddress();
            this.likes = place.getPlaceStatistic().getReviewCountGood();
            this.goodKeyWords = place.getGoodKeyWordsFromReviews();
        }
    }
    @Data
    @NoArgsConstructor
    public static class PlaceResponseRecommendDetailDto{
        private Long id;
        private String placeName;
        private String categoryName;
        private String thumbNailImageUrl;
        public PlaceResponseRecommendDetailDto(Place place){
            this.id =place.getId();
            this.placeName = place.getName();
            this.categoryName = place.getCategory().getName();
            this.thumbNailImageUrl = place.getThumbNailImageUrl();
        }
    }
    @Data
    public static class PlaceReviewCountSortResponseDto{
        private Long id ;
        private String placeName;
        private String categoryName;
        private String thumbNailImageUrl;
        private List<SingleDescriptionAndNickNameDto> reviews;
        public PlaceReviewCountSortResponseDto(Place place){
            this.id = place.getId();
            this.placeName = place.getName();
            this.categoryName = place.getCategory().getName();
            this.thumbNailImageUrl = place.getThumbNailImageUrl();
            this.reviews = place.getSingleDescriptionAndNickNameFromReviews();
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
        private double latitude;
        private double longitude;
        private List<String> facilityNames;
        public PlaceDetailDto(Place place){
            this.id=place.getId();
            this.name =place.getName();
            this.name = place.getName();
            this.describe = place.getDescribe();
            this.thumbNailImageUrl = place.getThumbNailImageUrl();
            this.address = place.getAddress();
            this.latitude = place.getKorLatitude();
            this.longitude = place.getKorLongitude();
            this.facilityNames = place.getFacilityNamesByPlace();
        }
    }
    @Data
    @NoArgsConstructor
    public static class PlaceResponseDetailDto{
        private Long id;
        private Long categoryId;
        private String name;
        private String describe;
        private int reviewCount;
        private String thumbNailImageUrl;
        private List<String> facilityNames;
        private String address;
        private double latitude;
        private double longitude;
        private int reviewCountBad;
        private int reviewCountGood;
        private int reviewCountIdk;
        private int smallDogGoodTotal;
        private int smallDogBadTotal;
        private int mediumDogGoodTotal;
        private int mediumDogBadTotal;
        private int largeDogGoodTotal;
        private int largeDogBadTotal;
        public PlaceResponseDetailDto(Place place, PlaceStatistic placeStatistic){
            this.id = place.getId();
            this.categoryId = place.getCategory().getId();
            this.name =place.getName();
            this.name = place.getName();
            this.describe = place.getDescribe();
            this.reviewCount = placeStatistic.getReviewCount();
            this.thumbNailImageUrl = place.getThumbNailImageUrl();
            this.address = place.getAddress();
            this.latitude = place.getKorLatitude();
            this.longitude = place.getKorLongitude();
            this.facilityNames = place.getFacilityNamesByPlace();

            this.reviewCountGood = placeStatistic.getReviewCountGood();
            this.reviewCountBad = placeStatistic.getReviewCountBad();
            this.reviewCountIdk = placeStatistic.getReviewCountIdk();
            this.smallDogGoodTotal = placeStatistic.getSmallDogGoodTotal();
            this.smallDogBadTotal = placeStatistic.getSmallDogBadTotal();
            this.mediumDogGoodTotal = placeStatistic.getMediumDogGoodTotal();
            this.mediumDogBadTotal = placeStatistic.getMediumDogBadTotal();
            this.largeDogGoodTotal = placeStatistic.getLargeDogGoodTotal();
            this.largeDogBadTotal = placeStatistic.getLargeDogBadTotal();
        }
    }
}
