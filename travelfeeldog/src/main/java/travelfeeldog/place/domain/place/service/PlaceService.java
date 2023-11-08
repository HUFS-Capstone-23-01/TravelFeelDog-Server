package travelfeeldog.place.domain.place.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.member.application.service.MemberService;
import travelfeeldog.place.domain.information.category.model.Category;
import travelfeeldog.place.domain.information.category.service.CategoryService;
import travelfeeldog.place.domain.information.location.model.Location;
import travelfeeldog.place.domain.information.location.service.LocationService;
import travelfeeldog.place.domain.place.model.KeyWord;
import travelfeeldog.place.domain.place.model.Place;
import travelfeeldog.place.domain.place.model.Places;
import travelfeeldog.place.domain.place.repository.PlaceRepository;
import travelfeeldog.place.dto.PlaceDtos.PlaceDetailDto;
import travelfeeldog.place.dto.PlaceDtos.PlacePostRequestDto;
import travelfeeldog.place.dto.PlaceDtos.PlaceResponseDetailDto;
import travelfeeldog.place.dto.PlaceDtos.PlaceResponseRecommendDetailDto;
import travelfeeldog.place.dto.PlaceDtos.PlaceReviewCountSortResponseDto;
import travelfeeldog.place.dto.PlaceDtos.PlaceSearchResponseDto;
import travelfeeldog.review.dto.ReviewDtos.ReviewPostRequestDto;

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
        Place place = create(category, location, placePostRequestDto);
        return new PlaceDetailDto(place);
    }

    @Transactional
    public Place create(Category category, Location location, PlacePostRequestDto request) {
        Place place = Place.RegisterNewPlace(request, category, location);
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

    public PlaceResponseDetailDto getPlaceDetailById(Long placeId) {
        Place place = getPlaceById(placeId);
        place.upCountPlaceViewCount();
        return new PlaceResponseDetailDto(place, place.getPlaceStatistic());
    }

    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }

    public List<PlaceResponseRecommendDetailDto> getResponseRecommend(String categoryName, String locationName) {
        Places places = placeRepository.findPlacesByLocationNameAndCategoryName(categoryName, locationName);
        return places.sortByViewCountLimit6();
    }

    public List<PlaceReviewCountSortResponseDto> getMostReviewPlace(String locationName) {
        Places placeSorted = placeRepository.findPlacesByLocationName(locationName).getPlacesSortByReviewCount();
        Places places = placeSorted.getFilterdPlacesByMostViews();
        if (places.isEmpty()) {
            places = placeSorted;
        }
        return places.getMostReviewPlacesDto(6);
    }

    public List<PlaceSearchResponseDto> getResponseSearch(String categoryName, String locationName, KeyWord keyWord) {
        Places places = placeRepository.findPlacesByLocationNameAndCategoryNameCallKey(categoryName, locationName);
        if (keyWord.isKeyWordIsEmpty()) {
            String normalizedKeyword = keyWord.getNoramalizedKeyWord();
            Places filteredPlaces = places.getFilterdPlaceByKeyWord(normalizedKeyword);
            Places filteredPlacesByName = places.getKeyWordMatchSamePlaces(normalizedKeyword);
            filteredPlaces.addALLPlaces(filteredPlacesByName);
            return filteredPlaces.getPlaceSearchByKeyWord(10);
        }

        return places.getPlaceSearchByKeyWord(10);
    }
}
