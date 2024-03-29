package travelfeeldog.placeinformation.presentation.api;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import travelfeeldog.placeinformation.information.facility.domain.model.Facility;
import travelfeeldog.placeinformation.information.facility.service.FacilityService;
import travelfeeldog.placeinformation.dto.PlaceDtos.PlaceDetailDto;
import travelfeeldog.placeinformation.place.domain.model.Place;
import travelfeeldog.placeinformation.place.service.PlaceService;
import travelfeeldog.placeinformation.information.placefacility.service.PlaceFacilityService;
import travelfeeldog.global.common.dto.ApiResponse;

@RestController
@RequestMapping("/places/{placeId}/facilities")
@RequiredArgsConstructor
public class PlaceFacilityApiController {
    private final PlaceFacilityService placeFacilityService;
    private final PlaceService placeService;
    private final FacilityService facilityService;

    @PostMapping
    public ApiResponse<PlaceDetailDto> addFacilityToPlace(@PathVariable Long placeId,
                                                          @RequestParam Long facilityId) {
        Place place = placeService.getPlaceById(placeId);
        Facility facility = facilityService.getFacilityById(facilityId);
        placeFacilityService.addFacilityToPlace(place, facility);
        return ApiResponse.success(new PlaceDetailDto(place));
    }

    @PostMapping(value = "/batch", produces = "application/json;charset=UTF-8")
    public ApiResponse<Void> addFacilitiesToPlace(@PathVariable Long placeId,
                                                  @RequestParam List<Long> facilityIds) {
        Place place = placeService.getPlaceById(placeId);
        List<Facility> facilities = facilityService.getFacilitiesByIds(facilityIds);
        placeFacilityService.addFacilitiesToPlace(place, facilities);
        return ApiResponse.success(HttpStatus.CREATED);
    }

    @PostMapping(value = "/batch/name", produces = "application/json;charset=UTF-8")
    public ApiResponse<PlaceDetailDto> addFacilitiesByNameToPlace(@PathVariable Long placeId,
                                                                  @RequestParam("names") List<String> names) {
        Place place = placeService.getPlaceById(placeId);
        List<Facility> facilities = facilityService.getFacilitiesByNames(names);
        facilities = facilities.stream().sorted(Comparator.comparing(Facility::getName)).collect(Collectors.toList());
        placeFacilityService.addFacilitiesToPlace(place, facilities);
        return ApiResponse.success(new PlaceDetailDto(place));
    }

    @DeleteMapping(value = "/{facilityId}", produces = "application/json;charset=UTF-8")
    public ApiResponse<Void> removeFacilityFromPlace(@PathVariable Long placeId,
                                                     @PathVariable Long facilityId) {
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
