package travelfeeldog.domain.location.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.domain.location.dao.LocationRepository;
import travelfeeldog.domain.location.dto.LocationDtos.RequestLocationDto;
import travelfeeldog.domain.location.model.Location;

@Transactional(readOnly = true)
@Service
public class LocationService {
    private LocationRepository locationRepository;

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }
    public Optional<Location> getLocationById(Long id) {
        return locationRepository.findById(id);
    }
    public Location getLocationByName(String name) {
        return locationRepository.findByName(name);
    }
    @Transactional
    public Location createLocation(RequestLocationDto request) {
        Location location = new Location();
        location.setName(request.getName());
        return locationRepository.save(location);
    }
    @Transactional
    public void updateLocation(Long id, Location location) {
        Optional<Location> optionalLocation = getLocationById(id);
        if (optionalLocation.isPresent()) {
            Location existingLocation = optionalLocation.get();
            existingLocation.setName(location.getName());
            locationRepository.save(existingLocation);
        }
    }
    @Transactional
    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }
}
