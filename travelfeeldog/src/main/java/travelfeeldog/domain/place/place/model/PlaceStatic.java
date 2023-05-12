package travelfeeldog.domain.place.place.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import travelfeeldog.domain.review.review.model.RecommendStatus;

@Getter @Setter
@Entity
public class PlaceStatic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="place__static_id")
    private Long id;

    @ColumnDefault("0")
    @Column(name="review_count_bad")
    private int reviewCountBad;
    @ColumnDefault("0")
    @Column(name="review_count_good")
    private int reviewCountGood;
    @ColumnDefault("0")
    @Column(name="review_count_idk")
    private int reviewCountIdk;

    @ColumnDefault("0")
    private int smallDogGoodTotal;
    @ColumnDefault("0")
    private int smallDogBadTotal;

    @ColumnDefault("0")
    private int mediumDogGoodTotal;
    @ColumnDefault("0")
    private int mediumDogBadTotal;

    @ColumnDefault("0")
    private int largeDogGoodTotal;
    @ColumnDefault("0")
    private int largeDogBadTotal;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    public void countAndUpdateResult(int[] dogNumbers, RecommendStatus recommendStatus) {
        switch (recommendStatus) {
            case GOOD -> {
                setReviewCountGood(getReviewCountGood() + 1);
                setSmallDogGoodTotal(getSmallDogGoodTotal() + dogNumbers[0]);
                setMediumDogGoodTotal(getMediumDogGoodTotal() + dogNumbers[1]);
                setLargeDogGoodTotal(getLargeDogGoodTotal() + dogNumbers[2]);
            }
            case IDK -> setReviewCountIdk(getReviewCountIdk() + 1);
            case BAD -> {
                setReviewCountBad(getReviewCountBad() + 1);
                setSmallDogBadTotal(getSmallDogBadTotal() + dogNumbers[0]);
                setMediumDogBadTotal(getMediumDogBadTotal() + dogNumbers[1]);
                setLargeDogBadTotal(getLargeDogBadTotal() + dogNumbers[2]);
            }
        }
    }

}
