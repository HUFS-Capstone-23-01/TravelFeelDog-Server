package travelfeeldog.domain.place.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.domain.place.dao.PlaceRepository;
import travelfeeldog.domain.place.model.Place;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    @Transactional
    public Place savePlace(){
        return placeRepository.save(new Place());
    }
    @Transactional
    public Place changeCategory(Place givenPlace){
        Place place = placeRepository.findById(givenPlace.getId()).get();
        place.setCategory(givenPlace.getCategory());
        return place;
    }
    public Place getOneByPlaceId(Long placeId){
        return placeRepository.findById(placeId).get();
    }
    public List<Place> getAllPlaces(Long placeId){
        return placeRepository.findAll();
    }

}
