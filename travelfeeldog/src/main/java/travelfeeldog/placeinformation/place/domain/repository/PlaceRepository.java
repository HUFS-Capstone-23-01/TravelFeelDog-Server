package travelfeeldog.placeinformation.place.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import travelfeeldog.placeinformation.place.domain.model.Place;
import travelfeeldog.placeinformation.place.domain.model.Places;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    @Query("SELECT DISTINCT p " +
            "FROM Place p " +
            "JOIN fetch p.location l " +
            "JOIN fetch p.category c " +
            "JOIN fetch p.reviews r " +
            "WHERE " +
            "c.name = :categoryName " +
            "AND l.name = :locationName ")
    Places findPlacesByLocationNameAndCategoryName(String categoryName, String locationName);


    @Query("SELECT DISTINCT p " +
            "FROM Place p " +
            "JOIN fetch p.location l " +
            "JOIN fetch p.category c " +
            "LEFT JOIN p.reviews r " +
            "LEFT JOIN r.reviewGoodKeyWords gk " +
            "LEFT JOIN gk.goodKeyWord gkw " +
            "WHERE " + "l.name = :locationName " + "AND c.name = :categoryName ")
    Places findPlacesByLocationNameAndCategoryNameCallKey(
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
    Places findPlacesByLocationName(@Param("locationName") String locationName);
}
