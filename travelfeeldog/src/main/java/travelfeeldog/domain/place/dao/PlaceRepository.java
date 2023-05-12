package travelfeeldog.domain.place.dao;

import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import travelfeeldog.domain.place.dto.PlaceSearchResponseDto;
import travelfeeldog.domain.place.model.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place,Long> {
    Place findByName(String name);
    List<Place> findPlacesByLocationNameAndCategoryName(String categoryName, String locationName);
    @Query("SELECT DISTINCT new travelfeeldog.domain.place.dto.PlaceSearchResponseDto(p.id,p.thumbNailImageUrl,p.name,p.address,ps.reviewCountGood)" +
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
            "AND gkw.keyWordName = :goodKeywordName ")
    List<PlaceSearchResponseDto> findByNameAndLocationAndCategoryAndKeyWord(
            @Param("locationName") String locationName,
            @Param("categoryName") String categoryName,
            @Param("goodKeywordName") String goodKeywordName);
    @Query("SELECT p " +
            "FROM Place p " +
            "JOIN p.placeStatic ps " +
            "JOIN p.location l " +
            "JOIN p.category c " +
            "JOIN p.reviews r " +
            "WHERE " +
            "l.name = :locationName ") // join fetch , place and member and review to call
    List<Place> getPlacesByLocationName(@Param("locationName") String locationName);
}
