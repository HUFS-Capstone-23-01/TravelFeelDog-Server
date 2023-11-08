package travelfeeldog.placeinformation.information.placefacility.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelfeeldog.placeinformation.information.facility.domain.model.Facility;
import travelfeeldog.placeinformation.information.placefacility.domain.PlaceFacilityRepository;
import travelfeeldog.placeinformation.place.domain.model.Place;

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
