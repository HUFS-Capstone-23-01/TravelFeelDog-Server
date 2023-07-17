package travelfeeldog.aggregate.place.place.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import travelfeeldog.aggregate.place.place.model.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place,Long> {
    Place findByName(String name);

    @Query("SELECT DISTINCT p " +
            "FROM Place p " +
            "JOIN fetch p.location l " +
            "JOIN fetch p.category c " +
            "JOIN fetch p.reviews r " +
            "WHERE " +
            "c.name = :categoryName " +
            "AND l.name = :locationName ")
    List<Place> findPlacesByLocationNameAndCategoryName(String categoryName, String locationName);


    @Query("SELECT DISTINCT p " +
            "FROM Place p " +
            "JOIN fetch p.location l " +
            "JOIN fetch p.category c " +
            "LEFT JOIN p.reviews r " +
            "LEFT JOIN r.reviewGoodKeyWords gk " +
            "LEFT JOIN gk.goodKeyWord gkw " +
            "WHERE " + "l.name = :locationName " + "AND c.name = :categoryName ")
    List<Place> findPlacesByLocationNameAndCategoryNameCallKey(
            @Param("categoryName") String categoryName,
            @Param("locationName") String locationName);

    @Query("SELECT DISTINCT p " +
            "FROM Place p " +
            "JOIN fetch p.location l " +
            "JOIN fetch p.category c " +
            "JOIN p.reviews r " +
            "WHERE " +
            "l.name = :locationName ")
        // join fetch , place and member and review to call
    List<Place> findPlacesByLocationName(@Param("locationName") String locationName);
}
