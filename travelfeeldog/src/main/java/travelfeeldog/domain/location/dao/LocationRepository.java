package travelfeeldog.domain.location.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travelfeeldog.domain.location.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location,Long> {
    Location findByName(String name);
}
