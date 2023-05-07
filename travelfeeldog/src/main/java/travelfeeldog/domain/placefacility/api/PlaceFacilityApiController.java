package travelfeeldog.domain.placefacility.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import travelfeeldog.domain.facility.model.Facility;
import travelfeeldog.domain.facility.service.FacilityService;
import travelfeeldog.domain.place.dto.PlaceDtos.PlaceDetailDto;
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
    public ApiResponse<PlaceDetailDto> addFacilityToPlace(@PathVariable Long placeId, @RequestParam Long facilityId) {
        Place place = placeService.getPlaceById(placeId);
        Facility facility = facilityService.getFacilityById(facilityId);
        placeFacilityService.addFacilityToPlace(place, facility);
        return ApiResponse.success(new PlaceDetailDto(place));
    }

    @PostMapping(value = "/batch",produces = "application/json;charset=UTF-8")
    public ApiResponse<Void> addFacilitiesToPlace(@PathVariable Long placeId, @RequestParam List<Long> facilityIds) {
        Place place = placeService.getPlaceById(placeId);
        List<Facility> facilities = facilityService.getFacilitiesByIds(facilityIds);
        placeFacilityService.addFacilitiesToPlace(place, facilities);
        return ApiResponse.success(HttpStatus.CREATED);
    }
    @PostMapping(value = "/batch/name",produces = "application/json;charset=UTF-8")
    public ApiResponse<PlaceDetailDto> addFacilitiesByNameToPlace(@PathVariable Long placeId, @RequestParam("names") List<String> names) {
        Place place = placeService.getPlaceById(placeId);
        List<Facility> facilities = facilityService.getFacilitiesByNames(names);
        placeFacilityService.addFacilitiesToPlace(place, facilities);
        return ApiResponse.success(new PlaceDetailDto(place));
    }

    @DeleteMapping(value = "/{facilityId}", produces = "application/json;charset=UTF-8")
    public ApiResponse<Void> removeFacilityFromPlace(@PathVariable Long placeId, @PathVariable Long facilityId) {
        Place place = placeService.getPlaceById(placeId);
        Facility facility = facilityService.getFacilityById(facilityId);
        placeFacilityService.removeFacilityFromPlace(place, facility);
        return ApiResponse.success(HttpStatus.OK);
    }

    @DeleteMapping(value = "/batch")
    public ApiResponse<Void> removeAllFacilitiesFromPlace(@PathVariable Long placeId) {
        Place place = placeService.getPlaceById(placeId);
        placeFacilityService.removeAllFacilitiesFromPlace(place);
        return ApiResponse.success(HttpStatus.OK);
    }

}
