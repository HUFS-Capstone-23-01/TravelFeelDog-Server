package travelfeeldog.aggregate.place.place.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travelfeeldog.aggregate.place.place.model.PlaceStatistic;

@Repository
public interface PlaceStatisticRepository  extends JpaRepository<PlaceStatistic,Long> {
    PlaceStatistic findByPlaceId(Long placeId);
}
