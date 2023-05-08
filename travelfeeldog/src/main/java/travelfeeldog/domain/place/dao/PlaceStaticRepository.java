package travelfeeldog.domain.place.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import travelfeeldog.domain.place.model.PlaceStatic;

public interface PlaceStaticRepository  extends JpaRepository<PlaceStatic,Long> {
    PlaceStatic findByPlaceId(Long placeId);
}
