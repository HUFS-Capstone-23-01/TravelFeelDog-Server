package travelfeeldog.aggregate.place.place.model;

import static javax.persistence.FetchType.LAZY;

import java.util.ArrayList;
import java.util.List;
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
import travelfeeldog.aggregate.place.category.model.Category;
import travelfeeldog.aggregate.place.location.model.Location;
import travelfeeldog.aggregate.place.place.dto.PlaceDtos.PlacePostRequestDto;
import travelfeeldog.aggregate.place.placefacility.model.PlaceFacility;
import travelfeeldog.aggregate.review.review.dto.ReviewDtos.ReviewPostRequestDto;
import travelfeeldog.aggregate.review.review.model.Review;
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
    private List<PlaceFacility> placeFacilities = new ArrayList<>();

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
        placeStatistic.addDogsInfo(request);
    }
}
