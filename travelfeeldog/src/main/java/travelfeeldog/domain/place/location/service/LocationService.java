package travelfeeldog.domain.place.location.service;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.domain.place.location.dao.LocationRepository;
import travelfeeldog.domain.place.location.dto.LocationDtos.RequestLocationDto;
import travelfeeldog.domain.place.location.model.Location;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }
    public Location getLocationById(Long id) {
        return locationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Location not found with ID: " + id));
    }
    public Location getLocationByName(String name) {
        return locationRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Location not found with name: " + name));
    }
    @Transactional
    public Location createLocation(RequestLocationDto request) {
        Location location = new Location();
        location.setName(request.getName());
        return locationRepository.save(location);
    }
    @Transactional
    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }
}
