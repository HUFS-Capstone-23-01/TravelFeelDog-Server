package travelfeeldog.placeinformation.information.location.service;

import java.util.List;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.placeinformation.information.location.domain.LocationRepository;
import travelfeeldog.placeinformation.information.location.domain.model.Location;
import travelfeeldog.placeinformation.dto.LocationDtos.RequestLocationDto;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Location getLocationById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found with ID: " + id));
    }

    public Location getLocationByName(String name) {
        return locationRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Location not found with name: " + name));
    }

    @Transactional
    public Location createLocation(RequestLocationDto request) {
        Location location = new Location(request.getName());
        return locationRepository.save(location);
    }

    @Transactional
    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }
}
