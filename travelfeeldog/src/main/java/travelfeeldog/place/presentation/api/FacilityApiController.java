package travelfeeldog.place.presentation.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import travelfeeldog.place.domain.information.facility.model.Facility;
import travelfeeldog.place.domain.information.facility.service.FacilityService;
import travelfeeldog.global.common.dto.ApiResponse;

@RestController
@RequestMapping("/facility")
@RequiredArgsConstructor
public class FacilityApiController {

    private final FacilityService facilityService;

    @GetMapping("/{facilityId}")
    public ApiResponse<Facility> getFacilityById(@PathVariable Long facilityId) {
        Facility facility = facilityService.getFacilityById(facilityId);
        return ApiResponse.success(facility);
    }

    @GetMapping(value = "/ids", produces = "application/json;charset=UTF-8")
    public ApiResponse<List<Facility>> getFacilitiesByIds(@RequestParam List<Long> ids) {
        List<Facility> facilities = facilityService.getFacilitiesByIds(ids);
        return ApiResponse.success(facilities);
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse<List<Facility>> getFacilitiesByNames(@RequestParam("names") List<String> names) {
        List<Facility> facilities = facilityService.getFacilitiesByNames(names);
        return ApiResponse.success(facilities);
    }

    @GetMapping(value = "/all", produces = "application/json;charset=UTF-8")
    public ApiResponse<List<Facility>> getAllFacilities() {
        List<Facility> facilities = facilityService.getAllFacilities();
        return ApiResponse.success(facilities);
    }

    @PostMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse<Facility> createFacility(@RequestBody String facilityName) {
        Facility facility = facilityService.createFacility(facilityName);
        return ApiResponse.success(facility);
    }

    @PutMapping("/{facilityId}")
    public ApiResponse<Facility> updateFacility(@PathVariable Long facilityId, @RequestBody Facility updatedFacility) {
        Facility facility = facilityService.updateFacility(facilityId, updatedFacility);
        return ApiResponse.success(facility);
    }

    @DeleteMapping("/{facilityId}")
    public ApiResponse<Void> deleteFacility(@PathVariable Long facilityId) {
        facilityService.deleteFacility(facilityId);
        return ApiResponse.success(null);
    }
}
