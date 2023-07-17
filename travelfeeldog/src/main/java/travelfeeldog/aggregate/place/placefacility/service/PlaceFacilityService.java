package travelfeeldog.aggregate.place.placefacility.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelfeeldog.aggregate.place.facility.model.Facility;
import travelfeeldog.aggregate.place.place.model.Place;
import travelfeeldog.aggregate.place.placefacility.repository.PlaceFacilityRepository;

@Service
@RequiredArgsConstructor
public class PlaceFacilityService {

    private final PlaceFacilityRepository placeFacilityRepository;

    public void addFacilityToPlace(Place place, Facility facility) {
        placeFacilityRepository.addFacilityToPlace(place, facility);
    }

    public void addFacilitiesToPlace(Place place, List<Facility> facilities) {
        placeFacilityRepository.addFacilitiesToPlace(place, facilities);
    }

    public void removeAllFacilitiesFromPlace(Place place) {
        placeFacilityRepository.removeAllFacilitiesFromPlace(place);
    }

    public void removeFacilityFromPlace(Place place, Facility facility) {
        placeFacilityRepository.removeFacilityFromPlace(place, facility);
    }


}
