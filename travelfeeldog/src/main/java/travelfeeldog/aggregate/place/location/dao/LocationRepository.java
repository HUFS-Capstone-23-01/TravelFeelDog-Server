package travelfeeldog.aggregate.place.location.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travelfeeldog.aggregate.place.location.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location,Long> {

    Optional<Location> findByName(String name);
}
