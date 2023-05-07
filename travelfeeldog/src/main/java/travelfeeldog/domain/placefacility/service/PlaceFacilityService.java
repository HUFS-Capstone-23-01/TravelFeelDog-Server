package travelfeeldog.domain.placefacility.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelfeeldog.domain.facility.model.Facility;
import travelfeeldog.domain.place.model.Place;
import travelfeeldog.domain.placefacility.repository.PlaceFacilityRepository;

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
