package travelfeeldog.domain.place.place.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import travelfeeldog.domain.place.category.model.Category;
import travelfeeldog.domain.place.category.service.CategoryService;
import travelfeeldog.domain.place.place.dto.PlaceDtos.PlaceDetailDto;
import travelfeeldog.domain.place.place.dto.PlaceDtos.PlacePostRequestDto;
import travelfeeldog.domain.place.place.dto.PlaceDtos.PlaceResponseDetailDto;
import travelfeeldog.domain.place.place.dto.PlaceDtos.PlaceResponseRecommendDetailDto;
import travelfeeldog.domain.place.place.dto.PlaceDtos.PlaceReviewCountSortResponseDto;
import travelfeeldog.domain.place.place.dto.PlaceDtos.PlaceSearchResponseDto;
import travelfeeldog.domain.place.place.model.Place;
import travelfeeldog.domain.place.place.model.PlaceStatistic;
import travelfeeldog.domain.place.location.model.Location;
import travelfeeldog.domain.place.location.service.LocationService;
import travelfeeldog.domain.member.domain.service.MemberService;
import travelfeeldog.domain.place.place.dao.PlaceRepository;
import travelfeeldog.domain.review.review.dto.ReviewDtos.ReviewPostRequestDto;
import travelfeeldog.domain.review.review.model.Review;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final CategoryService categoryService;
    private final LocationService locationService;
    private final MemberService memberService;

    public PlaceDetailDto addNewPlace(PlacePostRequestDto placePostRequestDto) {
        Category category = categoryService.getCategoryByName(placePostRequestDto.getCategoryName());
        Location location = locationService.getLocationByName(placePostRequestDto.getLocationName());
        Place place = create(category,location,placePostRequestDto);
        return new PlaceDetailDto(place);
    }
    @Transactional
    public Place create(Category category,Location location,PlacePostRequestDto request){
        Place place = Place.RegisterNewPlace(request,category,location);
        return placeRepository.save(place);
    }

    @Transactional
    public Place changeImageUrl(Long placeId, String imageUrl) {
        Place place = getPlaceById(placeId);
        place.modifyPlaceImageUrl(imageUrl);
        return place;
    }
    public Place getPlaceById(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(() -> new EntityNotFoundException("Place not found with ID: " + placeId));
    }
    public Place getPlaceById(ReviewPostRequestDto request) {
        return getPlaceById(request.getPlaceId());
    }

    public PlaceResponseDetailDto getPlaceDetailById(Long placeId, String token) {
        memberService.findByToken(token);
        Place place = getPlaceById(placeId);
        place.upCountPlaceViewCount();

        return new PlaceResponseDetailDto(place, place.getPlaceStatistic());
    }

    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }

    public List<PlaceResponseRecommendDetailDto> getResponseRecommend(String categoryName, String locationName,
                                                                      String token) {
        memberService.findByToken(token);
        List<Place> places = placeRepository.findPlacesByLocationNameAndCategoryName(categoryName, locationName);
        return  places.stream().sorted(Comparator.comparing(Place::getViewCount).reversed())
            .limit(6)
            .map(PlaceResponseRecommendDetailDto::new)
            .toList();
    }
    public List<PlaceReviewCountSortResponseDto> getMostReviewPlace(String locationName, String token) {
        memberService.findByToken(token);
        List<Place> placeSorted = placeRepository.findPlacesByLocationName(locationName)
                .stream()
                .map(Place::getPlaceStatistic)
                .sorted(Comparator.comparing(PlaceStatistic::getReviewCount).reversed())
                .map(PlaceStatistic::getPlace)
                .toList();
        List<Place> places = new ArrayList<>();
        for(Place place : placeSorted) {
            List<Review> reviews = place.getReviews();
            for(Review review: reviews) {
                if(!review.getAdditionalScript().isEmpty()) {
                    places.add(place);
                    break;
                }
            }
        }
        if(places.size() ==0) {
            places = placeSorted;
        }
        return places.stream()
                .limit(6)
                .map(PlaceReviewCountSortResponseDto::new)
                .toList();
    }
    public List<PlaceSearchResponseDto> getResponseSearch(String categoryName, String locationName, String keyWord,
                                                          String token) {
        memberService.findByToken(token);
        List<Place> places = placeRepository.findPlacesByLocationNameAndCategoryNameCallKey(categoryName, locationName);
        if(keyWord.trim().length() != 0) {

            String normalizedKeyword = keyWord.trim().toLowerCase();

            List<Place> filteredPlaces = new java.util.ArrayList<>(places.stream()
                    .filter(place -> place.getReviews().stream()
                            .flatMap(review -> review.getReviewGoodKeyWords().stream())
                            .map(reviewGoodKeyword -> reviewGoodKeyword.getGoodKeyWord().getKeyWordName().toLowerCase())
                            .anyMatch(keyword -> keyword.contains(normalizedKeyword)))
                    .toList());
            List<Place> filteredPlacesByName = places.stream()
                    .filter(place -> place.getName().contains(normalizedKeyword))
                    .toList();

            filteredPlaces.addAll(filteredPlacesByName);

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
