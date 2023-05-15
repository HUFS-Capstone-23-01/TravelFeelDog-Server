package travelfeeldog.domain.place.place.model;

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
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import travelfeeldog.domain.place.category.model.Category;
import travelfeeldog.domain.place.location.model.Location;
import travelfeeldog.domain.place.placefacility.model.PlaceFacility;
import travelfeeldog.domain.review.review.model.Review;
import travelfeeldog.global.common.model.BaseTimeEntity;


@DynamicInsert
@Entity
@Table(name ="places")
@Getter @Setter
public class Place extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="place_id")
    private Long id;
    @Column(name="place_name")
    private String name;
    @Column(name="place_decsribe")
    private String describe;
    @ColumnDefault("0")
    @Column(name="place_view_count")
    private int viewCount;
    @ColumnDefault("0")
    @Column(name="place_review_count")
    private int reviewCount;
    @ColumnDefault("'https://tavelfeeldog.s3.ap-northeast-2.amazonaws.com/base/pic1.JPG'")
    @Column(name="place_thumbnail_image")
    private String thumbNailImageUrl;
    @Column(name="place_latitude")
    private float latitude;
    @Column(name="place_longitude")
    private float longitude;
    @Column(name="place_address")
    private String address;

    @OneToMany(mappedBy = "place" ,cascade = CascadeType.PERSIST)
    private List<PlaceFacility> placeFacilities = new ArrayList<>();

    @OneToMany(mappedBy = "place" ,cascade = CascadeType.PERSIST)
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne(fetch = LAZY,cascade= CascadeType.PERSIST)
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne(fetch = LAZY,cascade= CascadeType.PERSIST)
    @JoinColumn(name="location_id")
    private Location location;

    @OneToOne(mappedBy = "place", cascade = CascadeType.ALL)
    private PlaceStatistic placeStatistic;
    public void updateReviewCount(){
        this.reviewCount += 1;
    }
}