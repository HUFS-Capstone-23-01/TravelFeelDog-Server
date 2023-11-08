package travelfeeldog.placeinformation.place.domain.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import travelfeeldog.placeinformation.dto.PlaceDtos.PlaceResponseRecommendDetailDto;
import travelfeeldog.placeinformation.dto.PlaceDtos.PlaceReviewCountSortResponseDto;
import travelfeeldog.placeinformation.dto.PlaceDtos.PlaceSearchResponseDto;
import travelfeeldog.review.reviewpost.domain.model.Review;

public class Places {
    private final List<Place> places;

    public Places(final List<Place> places) {
        this.places = places;
    }

    public List<PlaceResponseRecommendDetailDto> sortByViewCountLimit6() {
        return places.stream().sorted(Comparator.comparing(Place::getViewCount).reversed())
                .limit(6)
                .map(PlaceResponseRecommendDetailDto::new)
                .toList();
    }

    public Places getPlacesSortByReviewCount() {
        places.stream()
                .map(Place::getPlaceStatistic)
                .sorted(Comparator.comparing(PlaceStatistic::getReviewCount).reversed())
                .map(PlaceStatistic::getPlace)
                .toList();
        return this;
    }

    public Places getFilterdPlacesByMostViews() {
        List<Place> s = new ArrayList<>();
        for (Place place : this.places) {
            List<Review> reviews = place.getReviews();
            for (Review review : reviews) {
                if (!review.getAdditionalScript().isEmpty()) {
                    s.add(place);
                    break;
                }
            }
        }
        return new Places(s);
    }

    public boolean isEmpty() {
        return places.size() == 0;
    }

    public List<PlaceReviewCountSortResponseDto> getMostReviewPlacesDto(int number) {
        return places.stream()
                .limit(number)
                .map(PlaceReviewCountSortResponseDto::new)
                .toList();
    }

    public List<PlaceSearchResponseDto> getPlaceSearchByKeyWord(int number) {
        return this.places.stream()
                .map(PlaceSearchResponseDto::new)
                .limit(number)
                .toList();
    }

    public Places getFilterdPlaceByKeyWord(String normalizedKeyword) {
        return new Places(places.stream()
                .filter(place -> place.getReviews().stream()
                        .flatMap(review -> review.getReviewGoodKeyWords().stream())
                        .map(reviewGoodKeyword -> reviewGoodKeyword.getGoodKeyWord().getKeyWordName().toLowerCase())
                        .anyMatch(keyword -> keyword.contains(normalizedKeyword)))
                .toList());
    }

    public Places getKeyWordMatchSamePlaces(String normalizedKeyword) {
        return new Places(places.stream()
                .filter(place -> place.getName().contains(normalizedKeyword))
                .toList());
    }

    public void addALLPlaces(Places givenPlaces) {
        this.places.addAll(givenPlaces.places);
    }
}
