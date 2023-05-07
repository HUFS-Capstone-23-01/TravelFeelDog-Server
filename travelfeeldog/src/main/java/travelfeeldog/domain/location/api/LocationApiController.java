package travelfeeldog.domain.location.api;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import travelfeeldog.domain.location.dto.LocationDtos.RequestLocationDto;
import travelfeeldog.domain.location.model.Location;
import travelfeeldog.domain.location.service.LocationService;
import travelfeeldog.global.common.dto.ApiResponse;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationApiController {

    private final LocationService locationService;

    @GetMapping(value = "/all", produces = "application/json;charset=UTF-8")
    public ApiResponse<List<Location>> getAllLocations() {
        List<Location> locations = locationService.getAllLocations();
        return ApiResponse.success(locations);
    }
    @GetMapping(value = "/{id}", produces = "application/json;charset=UTF-8")
    public ApiResponse<Location> getLocationById(@PathVariable Long id) {
        Optional<Location> location = locationService.getLocationById(id);
        return location.map(value -> ApiResponse.success(value)).orElseGet(() -> ApiResponse.error(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/", produces = "application/json;charset=UTF-8")
    public ApiResponse<Location> getLocationByName(@RequestParam("name") String name) {
        Location location = locationService.getLocationByName(name);
        return location != null ? ApiResponse.success(location)
                : ApiResponse.error(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json;charset=UTF-8")
    public ApiResponse<Location> createLocation(@RequestBody RequestLocationDto request) {
        Location createdLocation = locationService.createLocation(request);
        return ApiResponse.success(createdLocation);
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json;charset=UTF-8")
    public ApiResponse<Void> updateLocation(@PathVariable Long id,@RequestBody Location location) {
        locationService.updateLocation(id, location);
        return ApiResponse.success(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json;charset=UTF-8")
    public ApiResponse<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ApiResponse.success(HttpStatus.NO_CONTENT);
    }
}
