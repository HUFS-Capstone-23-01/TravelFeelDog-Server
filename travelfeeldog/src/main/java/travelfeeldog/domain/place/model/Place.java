package travelfeeldog.domain.place.model;

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
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import travelfeeldog.domain.category.model.Category;
import travelfeeldog.domain.facility.model.Facility;
import travelfeeldog.domain.location.model.Location;
import travelfeeldog.domain.review.model.Review;
import travelfeeldog.global.common.model.BaseTimeEntity;


@DynamicInsert
@Setter
@Getter
@Entity
public class Place extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="place_id")
    private Long id;
    @Column(name="place_name")
    private String name;
    @Column(name="place_decsribe")
    private String describe;
    @ColumnDefault("0")
    @Column(name="place_view_count")
    private Integer viewCount;

    @ColumnDefault("'example.com'")
    @Column(name="place_thumbnail_image")
    private String thumbnailImageUrl;

    @OneToMany(mappedBy = "place" ,cascade = CascadeType.PERSIST)
    private List<Facility> facilities = new ArrayList<>();

    @OneToMany(mappedBy = "place" ,cascade = CascadeType.PERSIST)
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne(fetch = LAZY,cascade= CascadeType.PERSIST)
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne(fetch = LAZY,cascade= CascadeType.PERSIST)
    @JoinColumn(name="location_id")
    private Location location;

}