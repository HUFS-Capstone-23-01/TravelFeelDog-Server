package travelfeeldog.domain.place.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import travelfeeldog.domain.place.model.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place,Long> {
    Place findByName(String name);
    @Query("SELECT p " +
            "FROM Place p " +
            "JOIN p.location l " +
            "JOIN p.category c " +
            "JOIN p.reviews r " +
            "WHERE " +
            "l.name = :locationName " +
            "AND c.name = :categoryName ")
    List<Place> findPlacesByLocationNameAndCategoryName(String categoryName, String locationName);
    @Query("SELECT p " +
            "FROM Place p " +
            "JOIN p.placeStatic ps " +
            "JOIN p.location l " +
            "JOIN p.category c " +
            "JOIN p.reviews r " +
            "JOIN r.reviewGoodKeyWords gk " +
            "JOIN gk.goodKeyWord gkw " +
            "WHERE " +
            "l.name = :locationName " +
            "AND c.name = :categoryName " +
            "AND ( gkw.keyWordName = :goodKeywordName " +
            "OR p.name = :goodKeywordName )")
    List<Place> findByNameAndLocationAndCategoryAndKeyWord(
            @Param("categoryName") String categoryName,
            @Param("locationName") String locationName,
            @Param("goodKeywordName") String goodKeywordName);
    @Query("SELECT p " +
            "FROM Place p " +
            "JOIN p.location l " +
            "JOIN p.category c " +
            "JOIN p.reviews r " +
            "WHERE " +
            "l.name = :locationName ") // join fetch , place and member and review to call
    List<Place> findPlacesByLocationName(@Param("locationName") String locationName);
}
