package travelfeeldog.domain.place.place.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travelfeeldog.domain.place.place.model.PlaceStatic;

@Repository
public interface PlaceStaticRepository  extends JpaRepository<PlaceStatic,Long> {
    PlaceStatic findByPlaceId(Long placeId);
}
