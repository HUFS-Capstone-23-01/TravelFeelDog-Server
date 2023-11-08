package travelfeeldog.placeinformation.place.domain.model;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import travelfeeldog.global.common.domain.basetime.BaseTimeEntity;
import travelfeeldog.placeinformation.information.category.domain.model.Category;
import travelfeeldog.placeinformation.information.location.domain.model.Location;
import travelfeeldog.placeinformation.information.placefacility.domain.model.PlaceFacility;
import travelfeeldog.placeinformation.dto.PlaceDtos.PlacePostRequestDto;
import travelfeeldog.review.reviewpost.domain.model.Review;
import travelfeeldog.review.dto.ReviewDtos.ReviewPostRequestDto;
import travelfeeldog.review.dto.ReviewDtos.SingleDescriptionAndNickNameDto;


@DynamicInsert
@Entity
@Table(name = "places")
@Getter
public class Place extends BaseTimeEntity {
    private final static int KOREA_BASE_LATITUDE = 30;
    private final static int KOREA_BASE_LONGITUDE = 120;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long id;
    @Column(name = "place_name")
    private String name;
    @Column(name = "place_decsribe")
    private String describe;

    @ColumnDefault("'/base/baseLogo.png'")
    @Column(name = "place_thumbnail_image")
    private String thumbNailImageUrl;
    @Column(name = "place_latitude")
    private double latitude;
    @Column(name = "place_longitude")
    private double longitude;
    @Column(name = "place_address")
    private String address;
    @ColumnDefault("0")
    @Column(name = "place_view_count")
    private int viewCount;

    @OneToMany(mappedBy = "place", cascade = CascadeType.PERSIST)
    private Set<PlaceFacility> placeFacilities = new HashSet<>();
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne(fetch = LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "location_id")
    private Location location;
    @OneToOne(mappedBy = "place", cascade = CascadeType.ALL)
    private PlaceStatistic placeStatistic;

    protected Place() {

    }

    public static Place RegisterNewPlace(PlacePostRequestDto placePostRequestDto, Category category,
                                         Location location) {
        return new Place(placePostRequestDto, category, location);
    }

    private Place(PlacePostRequestDto placePostRequestDto, Category category, Location location) {
        this(placePostRequestDto.getName(), placePostRequestDto.getDescribe(), placePostRequestDto.getAddress(),
                placePostRequestDto.getLatitude(), placePostRequestDto.getLongitude(), category, location);
        this.placeStatistic = new PlaceStatistic(this);
    }

    private Place(String name, String describe, String address, double latitude, double longitude,
                  Category category, Location location) {
        this.name = name;
        this.describe = describe;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.location = location;
    }


    public void upCountPlaceViewCount() {
        this.viewCount += 1;
    }

    public void modifyPlaceImageUrl(String thumbNailImageUrl) {
        this.thumbNailImageUrl = thumbNailImageUrl;
    }

    public void updatePlaceStatistic(ReviewPostRequestDto request) {
        this.placeStatistic.addDogsInfo(request);
    }

    public List<String> getFacilityNamesByPlace() {
        return this.placeFacilities.stream()
                .map(pf -> pf.getFacility().getName())
                .toList();
    }

    public List<String> getGoodKeyWordsFromReviews() {
        return this.reviews.stream()
                .flatMap(review -> review.getReviewGoodKeyWords().stream())
                .map(goodKeyWord -> goodKeyWord.getGoodKeyWord().getKeyWordName())
                .distinct()
                .toList();
    }

    public double getKorLatitude() {
        return (this.latitude + KOREA_BASE_LATITUDE);
    }

    public double getKorLongitude() {
        return (this.longitude + KOREA_BASE_LONGITUDE);
    }

    public List<SingleDescriptionAndNickNameDto> getSingleDescriptionAndNickNameFromReviews() {
        return this.reviews.stream()
                .map(SingleDescriptionAndNickNameDto::new)
                .filter(r -> !r.getAdditionalScript().isEmpty())
                .toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Place place = (Place) o;
        return Double.compare(place.latitude, latitude) == 0
                && Double.compare(place.longitude, longitude) == 0 && viewCount == place.viewCount
                && Objects.equals(id, place.id) && Objects.equals(name, place.name)
                && Objects.equals(describe, place.describe) && Objects.equals(thumbNailImageUrl,
                place.thumbNailImageUrl) && Objects.equals(address, place.address) && Objects.equals(
                placeFacilities, place.placeFacilities) && Objects.equals(reviews, place.reviews)
                && Objects.equals(category, place.category) && Objects.equals(location, place.location)
                && Objects.equals(placeStatistic, place.placeStatistic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, describe, thumbNailImageUrl, latitude, longitude, address, viewCount,
                placeFacilities,
                reviews, category, location, placeStatistic);
    }

}
