package travelfeeldog.domain.place.service;


import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import travelfeeldog.domain.category.model.Category;
import travelfeeldog.domain.category.service.CategoryService;
import travelfeeldog.domain.location.model.Location;
import travelfeeldog.domain.location.service.LocationService;
import travelfeeldog.domain.member.service.MemberService;
import travelfeeldog.domain.place.dao.PlaceRepository;
import travelfeeldog.domain.place.dao.PlaceStaticRepository;
import travelfeeldog.domain.place.dto.PlaceDtos.PlaceDetailDto;
import travelfeeldog.domain.place.dto.PlaceDtos.PlacePostRequestDto;
import travelfeeldog.domain.place.dto.PlaceDtos.PlaceResponseDetailDto;
import travelfeeldog.domain.place.dto.PlaceDtos.PlaceResponseRecommendDetailDto;
import travelfeeldog.domain.place.dto.PlaceDtos.PlaceReviewCountSortResponseDto;
import travelfeeldog.domain.place.dto.PlaceDtos.PlaceSearchResponseDto;
import travelfeeldog.domain.place.model.Place;
import travelfeeldog.domain.place.model.PlaceStatic;
import travelfeeldog.domain.review.dto.ReviewDtos.ReviewPostRequestDto;


@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final PlaceStaticRepository placeStaticRepository;
    private final CategoryService categoryService;
    private final LocationService locationService;
    private final MemberService memberService;
    @Transactional
    public PlaceDetailDto addNewPlace(PlacePostRequestDto placePostRequestDto) {
        Place place = new Place();
        place.setName(placePostRequestDto.getName());
        place.setDescribe(placePostRequestDto.getDescribe());
        place.setAddress(placePostRequestDto.getAddress());
        place.setLatitude(placePostRequestDto.getLatitude());
        place.setLongitude(placePostRequestDto.getLongitude());

        Category category = categoryService.getCategoryByName(placePostRequestDto.getCategoryName());
        place.setCategory(category);

        Location location = locationService.getLocationByName(placePostRequestDto.getLocationName());
        place.setLocation(location);
        PlaceStatic placeStatic = new PlaceStatic();
        placeStatic.setPlace(place);

        placeRepository.save(place);
        placeStaticRepository.save(placeStatic);
        return new PlaceDetailDto(place);
    }

    @Transactional
    public Place changeCategory(Long placeId,String categoryName) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new EntityNotFoundException("Place not found with ID"));
        place.setCategory(categoryService.getCategoryByName(categoryName));
        return place;
    }
    @Transactional
    public Place changeImageUrl(Long placeId,String imageUrl) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new EntityNotFoundException("Place not found with ID"));
        place.setThumbNailImageUrl(imageUrl);
        return place;
    }
    public List<PlaceSearchResponseDto> getResponseSearch(String categoryName, String locationName, String PlaceName, String token){
        memberService.findByToken(token);
        List<Place> result = placeRepository.findByNameAndLocationAndCategoryAndKeyWord(categoryName, locationName, PlaceName);
        return result.stream().map(PlaceSearchResponseDto::new).limit(10).toList();
    }
    public Place getPlaceById(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(() -> new EntityNotFoundException("Place not found with ID: " + placeId));
    }
    public PlaceResponseDetailDto getPlaceDetailById(Long placeId,String token) {
        memberService.findByToken(token);
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new EntityNotFoundException("Place not found with ID: " + placeId));
        PlaceStatic placeStatic = placeStaticRepository.findByPlaceId(placeId);
        return new PlaceResponseDetailDto(place,placeStatic);
    }
    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }
    @Transactional
    public void addPlaceStatic(ReviewPostRequestDto requestDto) {
        PlaceStatic placeStatic = placeStaticRepository.findByPlaceId(requestDto.getPlaceId());
        int[] dogNumbers = new int[3];
        dogNumbers[0] = requestDto.getSmallDogNumber();
        dogNumbers[1] = requestDto.getMediumDogNumber();
        dogNumbers[2] = requestDto.getLargeDogNumber();
        placeStatic.countAndUpdateResult(dogNumbers,requestDto.getRecommendStatus());
    }
    public List<PlaceResponseRecommendDetailDto> getResponseRecommend(String categoryName, String locationName, String token) {
        memberService.findByToken(token);
        return placeRepository.findPlacesByLocationNameAndCategoryName(categoryName,locationName).stream().limit(6).map(PlaceResponseRecommendDetailDto::new).toList();
    }
    public List<PlaceReviewCountSortResponseDto> getMostReviewPlace(String locationName, String token){
        memberService.findByToken(token);
        List<Place> places = placeRepository.findPlacesByLocationName(locationName)
                                                                .stream()
                                                                .sorted(Comparator.comparing(Place::getReviewCount).reversed())
                                                                .toList();
        return  places.stream().limit(6).map(PlaceReviewCountSortResponseDto::new).toList() ;
    }
}
