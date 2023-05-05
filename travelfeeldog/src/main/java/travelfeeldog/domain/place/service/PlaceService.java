package travelfeeldog.domain.place.service;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.domain.category.dao.CategoryRepository;
import travelfeeldog.domain.category.model.Category;
import travelfeeldog.domain.location.dao.LocationRepository;
import travelfeeldog.domain.location.model.Location;
import travelfeeldog.domain.place.dao.PlaceRepository;
import travelfeeldog.domain.place.dto.PlaceDtos.PlacePostRequestDto;
import travelfeeldog.domain.place.model.Place;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    @Transactional
    public Place addNewPlace(PlacePostRequestDto placePostRequestDto) {
        Place place = new Place();
        place.setName(placePostRequestDto.getName());
        place.setDescribe(placePostRequestDto.getDescribe());
        place.setThumbNailImageUrl(placePostRequestDto.getThumbnailImageUrl());
        place.setAddress(placePostRequestDto.getAddress());
        place.setLatitude(placePostRequestDto.getLatitude());
        place.setLongitude(placePostRequestDto.getLongitude());

        Category category = categoryRepository.findByName(placePostRequestDto.getCategoryName());
        place.setCategory(category);

        Location location = locationRepository.findByName(placePostRequestDto.getLocationName());
        place.setLocation(location);

        return placeRepository.save(place);
    }

    @Transactional
    public Place changeCategory(Place givenPlace) {
        Place place = placeRepository.findById(givenPlace.getId())
                .orElseThrow(() -> new EntityNotFoundException("Place not found with ID: " + givenPlace.getId()));
        place.setCategory(givenPlace.getCategory());
        return place;
    }

    public Place getOneByPlaceId(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(() -> new EntityNotFoundException("Place not found with ID: " + placeId));
    }

    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }

}
