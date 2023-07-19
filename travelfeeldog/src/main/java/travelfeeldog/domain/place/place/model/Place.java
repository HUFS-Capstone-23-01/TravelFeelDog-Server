package travelfeeldog.domain.place.place.model;

import static java.util.stream.Collectors.toList;
import static javax.persistence.FetchType.LAZY;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import travelfeeldog.domain.place.category.model.Category;
import travelfeeldog.domain.place.facility.model.Facility;
import travelfeeldog.domain.place.location.model.Location;
import travelfeeldog.domain.place.place.dto.PlaceDtos.PlacePostRequestDto;
import travelfeeldog.domain.place.placefacility.model.PlaceFacility;
import travelfeeldog.domain.review.review.dto.ReviewDtos.ReviewPostRequestDto;
import travelfeeldog.domain.review.review.model.Review;
import travelfeeldog.global.common.domain.model.BaseTimeEntity;


@DynamicInsert
@Entity
@Table(name = "places")
@Getter
public class Place extends BaseTimeEntity {

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
    private float latitude;
    @Column(name = "place_longitude")
    private float longitude;
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

    public Place(PlacePostRequestDto placePostRequestDto,Category category,Location location) {
        this.placeStatistic = new PlaceStatistic(this);
        this.name = placePostRequestDto.getName();
        this.describe = placePostRequestDto.getDescribe();
        this.address = placePostRequestDto.getAddress();
        this.latitude = placePostRequestDto.getLatitude();
        this.longitude = placePostRequestDto.getLongitude();
        this.category = category;
        this.location = location;
    }
    public void upCountPlaceViewCount() {
        this.viewCount += 1;
    }
    public void modifyPlaceImageUrl(String thumbNailImageUrl) {
        this.thumbNailImageUrl = thumbNailImageUrl;
    }
    public void updatePlaceStatistic(ReviewPostRequestDto request){
        this.placeStatistic.addDogsInfo(request);
    }
    public List<String> getFacilityNamesByPlace(){
        return this.placeFacilities.stream()
            .map(pf -> pf.getFacility().getName())
            .toList();
    }
}
