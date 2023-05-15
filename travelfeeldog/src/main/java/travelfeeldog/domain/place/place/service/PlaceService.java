package travelfeeldog.domain.place.place.service;

import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import travelfeeldog.domain.place.category.model.Category;
import travelfeeldog.domain.place.category.service.CategoryService;
import travelfeeldog.domain.place.location.model.Location;
import travelfeeldog.domain.place.location.service.LocationService;
import travelfeeldog.domain.member.service.MemberService;
import travelfeeldog.domain.place.place.dao.PlaceRepository;
import travelfeeldog.domain.place.place.dao.PlaceStatisticRepository;
import travelfeeldog.domain.place.place.dto.PlaceDtos.PlaceDetailDto;
import travelfeeldog.domain.place.place.dto.PlaceDtos.PlacePostRequestDto;
import travelfeeldog.domain.place.place.dto.PlaceDtos.PlaceResponseDetailDto;
import travelfeeldog.domain.place.place.dto.PlaceDtos.PlaceResponseRecommendDetailDto;
import travelfeeldog.domain.place.place.dto.PlaceDtos.PlaceReviewCountSortResponseDto;
import travelfeeldog.domain.place.place.dto.PlaceDtos.PlaceSearchResponseDto;
import travelfeeldog.domain.place.place.model.Place;
import travelfeeldog.domain.place.place.model.PlaceStatistic;
import travelfeeldog.domain.review.review.dto.ReviewDtos.ReviewPostRequestDto;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final PlaceStatisticRepository placeStatisticRepository;
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

        PlaceStatistic placeStatistic = new PlaceStatistic();
        placeStatistic.setPlace(place);

        placeRepository.save(place);
        placeStatisticRepository.save(placeStatistic);

        return new PlaceDetailDto(place);
    }

    @Transactional
    public Place changeCategory(Long placeId, String categoryName) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new EntityNotFoundException("Place not found with ID"));
        place.setCategory(categoryService.getCategoryByName(categoryName));
        return place;
    }

    @Transactional
    public Place changeImageUrl(Long placeId, String imageUrl) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new EntityNotFoundException("Place not found with ID"));
        place.setThumbNailImageUrl(imageUrl);
        return place;
    }

    @Transactional
    public void addPlaceStatic(ReviewPostRequestDto requestDto) {
        PlaceStatistic placeStatistic = placeStatisticRepository.findByPlaceId(requestDto.getPlaceId());
        int[] dogNumbers = new int[3];
        dogNumbers[0] = requestDto.getSmallDogNumber();
        dogNumbers[1] = requestDto.getMediumDogNumber();
        dogNumbers[2] = requestDto.getLargeDogNumber();
        placeStatistic.countAndUpdateResult(dogNumbers, requestDto.getRecommendStatus());
    }

    public Place getPlaceById(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(() -> new EntityNotFoundException("Place not found with ID: " + placeId));
    }
    public Place getPlaceById(ReviewPostRequestDto request){
        return getPlaceById(request.getPlaceId());
    }

    public PlaceResponseDetailDto getPlaceDetailById(Long placeId, String token) {
        memberService.findByToken(token);

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new EntityNotFoundException("Place not found with ID: " + placeId));

        PlaceStatistic placeStatistic = placeStatisticRepository.findByPlaceId(placeId);

        return new PlaceResponseDetailDto(place, placeStatistic);
    }

    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }

    public List<PlaceResponseRecommendDetailDto> getResponseRecommend(String categoryName, String locationName,
                                                                      String token) {
        memberService.findByToken(token);

        return placeRepository.findPlacesByLocationNameAndCategoryName(categoryName, locationName)
                .stream()
                .limit(6)
                .map(PlaceResponseRecommendDetailDto::new)
                .toList();
    }
    public List<PlaceReviewCountSortResponseDto> getMostReviewPlace(String locationName, String token) {
        memberService.findByToken(token);
        List<Place> places = placeRepository.findPlacesByLocationName(locationName)
                .stream()
                .sorted(Comparator.comparing(Place::getReviewCount).reversed())
                .toList();

        return places.stream()
                .limit(6)
                .map(PlaceReviewCountSortResponseDto::new)
                .toList();
    }
    public List<PlaceSearchResponseDto> getResponseSearch(String categoryName, String locationName, String keyWord,
                                                          String token) {
        memberService.findByToken(token);
        List<Place> places = placeRepository.findPlacesByLocationNameAndCategoryNameCallKey(categoryName, locationName);
        if(keyWord.trim().length() != 0)
        {
        String normalizedKeyword = keyWord.trim().toLowerCase();

        List<Place> filteredPlaces = places.stream()
                .filter(place -> place.getReviews().stream()
                        .flatMap(review -> review.getReviewGoodKeyWords().stream())
                        .map(reviewGoodKeyword -> reviewGoodKeyword.getGoodKeyWord().getKeyWordName().toLowerCase())
                        .anyMatch(keyword -> keyword.toLowerCase().contains(normalizedKeyword)))
                .toList();
            return filteredPlaces.stream()
                    .map(PlaceSearchResponseDto::new)
                    .limit(10)
                    .toList();
        }
        return places.stream()
                .map(PlaceSearchResponseDto::new)
                .limit(10)
                .toList();
    }
}