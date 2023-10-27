package travelfeeldog.place.presentation.api;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import travelfeeldog.place.dto.LocationDtos.RequestLocationDto;
import travelfeeldog.place.dto.LocationDtos.ResponseLocationDto;
import travelfeeldog.place.domain.information.location.model.Location;
import travelfeeldog.place.domain.information.location.service.LocationService;
import travelfeeldog.global.common.dto.ApiResponse;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationApiController {

    private final LocationService locationService;

    @GetMapping(value = "/all", produces = "application/json;charset=UTF-8")
    public ApiResponse<List<ResponseLocationDto>> getAllLocations() {
        List<ResponseLocationDto> locations = locationService.getAllLocations().stream().map(ResponseLocationDto::new)
                .collect(
                        Collectors.toList());
        return ApiResponse.success(locations);
    }

    @GetMapping(value = "/{locationId}", produces = "application/json;charset=UTF-8")
    public ApiResponse<ResponseLocationDto> getLocationById(@PathVariable Long locationId) {
        Location location = locationService.getLocationById(locationId);
        return ApiResponse.success(new ResponseLocationDto(location));
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse<Location> getLocationByName(@RequestParam("name") String name) {
        Location location = locationService.getLocationByName(name);
        return location != null ? ApiResponse.success(location)
                : ApiResponse.error(HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = "application/json", produces = "application/json;charset=UTF-8")
    public ApiResponse<Location> createLocation(@RequestBody RequestLocationDto request) {
        Location createdLocation = locationService.createLocation(request);
        return ApiResponse.success(createdLocation);
    }

    @DeleteMapping(value = "/{locationId}", produces = "application/json;charset=UTF-8")
    public ApiResponse<Void> deleteLocation(@PathVariable Long locationId) {
        locationService.deleteLocation(locationId);
        return ApiResponse.success(HttpStatus.NO_CONTENT);
    }
}
