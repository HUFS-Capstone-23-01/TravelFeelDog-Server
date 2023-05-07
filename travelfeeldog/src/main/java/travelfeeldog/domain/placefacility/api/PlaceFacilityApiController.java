package travelfeeldog.domain.placefacility.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travelfeeldog.domain.facility.model.Facility;
import travelfeeldog.domain.facility.service.FacilityService;
import travelfeeldog.domain.place.model.Place;
import travelfeeldog.domain.place.service.PlaceService;
import travelfeeldog.domain.placefacility.service.PlaceFacilityService;
import travelfeeldog.global.common.dto.ApiResponse;

@RestController
@RequestMapping("/places/{placeId}/facilities")
@RequiredArgsConstructor
public class PlaceFacilityApiController {

    private final PlaceFacilityService placeFacilityService;
    private final PlaceService placeService;
    private final FacilityService facilityService;

    @PostMapping
    public ApiResponse<Void> addFacilityToPlace(@PathVariable Long placeId, @RequestBody Long facilityId) {
        Place place = placeService.getPlaceById(placeId);
        Facility facility = facilityService.getFacilityById(facilityId);
        placeFacilityService.addFacilityToPlace(place, facility);
        return ApiResponse.success(HttpStatus.CREATED);
    }

    @PostMapping("/batch")
    public ApiResponse<Void> addFacilitiesToPlace(@PathVariable Long placeId, @RequestBody List<Long> facilityIds) {
        Place place = placeService.getPlaceById(placeId);
        List<Facility> facilities = facilityService.getFacilitiesByIds(facilityIds);
        placeFacilityService.addFacilitiesToPlace(place, facilities);
        return ApiResponse.success(HttpStatus.CREATED);
    }
    @PostMapping("/batch/name")
    public ApiResponse<Void> addFacilitiesByNameToPlace(@PathVariable Long placeId, @RequestBody List<String> facilityNames) {
        Place place = placeService.getPlaceById(placeId);
        List<Facility> facilities = facilityService.getFacilitiesByNames(facilityNames);
        placeFacilityService.addFacilitiesToPlace(place, facilities);
        return ApiResponse.success(HttpStatus.CREATED);
    }

    @DeleteMapping("/{facilityId}")
    public ApiResponse<Void> removeFacilityFromPlace(@PathVariable Long placeId, @PathVariable Long facilityId) {
        Place place = placeService.getPlaceById(placeId);
        Facility facility = facilityService.getFacilityById(facilityId);
        placeFacilityService.removeFacilityFromPlace(place, facility);
        return ApiResponse.success(HttpStatus.OK);
    }

    @DeleteMapping("/batch")
    public ApiResponse<Void> removeAllFacilitiesFromPlace(@PathVariable Long placeId) {
        Place place = placeService.getPlaceById(placeId);
        placeFacilityService.removeAllFacilitiesFromPlace(place);
        return ApiResponse.success(HttpStatus.OK);
    }

}
