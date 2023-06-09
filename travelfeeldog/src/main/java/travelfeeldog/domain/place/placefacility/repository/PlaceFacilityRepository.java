package travelfeeldog.domain.place.placefacility.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travelfeeldog.domain.place.facility.model.Facility;
import travelfeeldog.domain.place.placefacility.model.PlaceFacility;
import travelfeeldog.domain.place.place.model.Place;

@Repository
public interface PlaceFacilityRepository extends JpaRepository<PlaceFacility,Long> {
    List<PlaceFacility> findByPlace(Place place);

    default void addFacilityToPlace(Place place, Facility facility) {
        PlaceFacility placeFacility = new PlaceFacility();
        placeFacility.setPlace(place);
        placeFacility.setFacility(facility);
        save(placeFacility);
    }
    default void addFacilitiesToPlace(Place place, List<Facility> facilities) {
        for (Facility facility : facilities) {
            addFacilityToPlace(place, facility);
        }
    }
    default void removeAllFacilitiesFromPlace(Place place) {
        List<PlaceFacility> placeFacilities = findByPlace(place);
        deleteAll(placeFacilities);
    }

    default void removeFacilityFromPlace(Place place, Facility facility) {
        deleteByPlaceAndFacility(place, facility);
    }
    void deleteByPlaceAndFacility(Place place, Facility facility);
}
