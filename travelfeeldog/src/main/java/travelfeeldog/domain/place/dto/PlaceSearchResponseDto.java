package travelfeeldog.domain.place.dto;

import lombok.Getter;

@Getter
public class PlaceSearchResponseDto {
    private Long id;
    private String thumbNailImageUrl;
    private String name;
    private String address;
    private int likes;

    public PlaceSearchResponseDto(Long placeId , String thumbNailImageUrl,String placeName, String placeAddress,int numberOfLikes) {
        this.id = placeId;
        this.thumbNailImageUrl = thumbNailImageUrl;
        this.name = placeName;
        this.address = placeAddress;
        this.likes = numberOfLikes;
    }
}
