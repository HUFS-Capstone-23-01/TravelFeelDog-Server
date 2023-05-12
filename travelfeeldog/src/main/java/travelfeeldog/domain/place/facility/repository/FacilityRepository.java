package travelfeeldog.domain.place.facility.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travelfeeldog.domain.place.facility.model.Facility;

@Repository
public interface FacilityRepository extends JpaRepository<Facility,Long> {
    Facility findByName(String name);
    List<Facility> findByNameIn(List<String> names);

}
