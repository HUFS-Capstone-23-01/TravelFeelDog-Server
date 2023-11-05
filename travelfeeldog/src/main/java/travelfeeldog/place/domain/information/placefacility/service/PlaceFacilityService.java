package travelfeeldog.place.domain.information.placefacility.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelfeeldog.place.domain.information.facility.model.Facility;
import travelfeeldog.place.domain.information.placefacility.repository.PlaceFacilityRepository;
import travelfeeldog.place.domain.place.model.Place;

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