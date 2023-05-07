package travelfeeldog.domain.facility.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travelfeeldog.domain.facility.model.Facility;
@Repository
public interface FacilityRepository extends JpaRepository<Facility,Long> {
}
