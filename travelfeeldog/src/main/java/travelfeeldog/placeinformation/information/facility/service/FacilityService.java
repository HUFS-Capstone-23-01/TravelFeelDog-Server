package travelfeeldog.placeinformation.information.facility.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.placeinformation.information.facility.domain.model.Facility;
import travelfeeldog.placeinformation.information.facility.domain.FacilityRepository;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;

    public Facility getFacilityById(Long id) {
        return facilityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Facility not found with id: " + id));
    }

    public List<Facility> getFacilitiesByIds(List<Long> facilitiesIds) {
        return facilityRepository.findAllById(facilitiesIds);
    }

    public List<Facility> getFacilitiesByNames(List<String> facilitiesNames) {
        return facilityRepository.findByNameIn(facilitiesNames);
    }

    public List<Facility> getAllFacilities() {
        return facilityRepository.findAll();
    }

    @Transactional
    public Facility createFacility(String name) {
        Facility facility = new Facility(name);
        return facilityRepository.save(facility);
    }

    @Transactional
    public Facility updateFacility(Long id, Facility updatedFacility) {
        Facility facility = getFacilityById(id);
        facility.updateName(updatedFacility.getName());
        return facilityRepository.save(facility);
    }

    @Transactional
    public void deleteFacility(Long id) {
        Facility facility = getFacilityById(id);
        facilityRepository.delete(facility);
    }
}
