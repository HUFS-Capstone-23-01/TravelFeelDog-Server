package travelfeeldog.domain.place.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travelfeeldog.domain.place.model.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place,Long> {
}
