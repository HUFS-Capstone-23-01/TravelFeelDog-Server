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
import lombok.NoArgsConstructor;

import org.hibernate.annotations.ColumnDefault;
import travelfeeldog.domain.review.review.dto.ReviewDtos.ReviewPostRequestDto;
import travelfeeldog.domain.review.review.model.RecommendStatus;

@NoArgsConstructor
@Getter
@Entity
public class PlaceStatistic {

    private final static int SMALL_DOG_CATEGORY_NUMBER = 0;
    private final static int MID_DOG_CATEGORY_NUMBER = 1;
    private final static int LARGE_DOG_CATEGORY_NUMBER = 2;
    private final static int TOTAL_DOG_CATEGORY_SIZE = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="place__static_id")
    private Long id;
    @ColumnDefault("0")
    @Column(name="place_review_count")
    private int reviewCount;
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
    public PlaceStatistic(Place place) {
        this.place = place;
    }
    public void countAndUpdateResult(int[] dogNumbers, RecommendStatus recommendStatus) {
        switch (recommendStatus) {
            case GOOD -> {
                this.reviewCountGood += 1;
                this.smallDogGoodTotal = this.smallDogGoodTotal + dogNumbers[SMALL_DOG_CATEGORY_NUMBER];
                this.mediumDogGoodTotal = this.mediumDogBadTotal +dogNumbers[MID_DOG_CATEGORY_NUMBER];
                this.largeDogBadTotal = this.largeDogGoodTotal + dogNumbers[LARGE_DOG_CATEGORY_NUMBER];
            }
            case IDK -> this.reviewCountIdk += 1 ;
            case BAD -> {
                this.reviewCountBad += 1;
                this.smallDogBadTotal = this.smallDogBadTotal +dogNumbers[SMALL_DOG_CATEGORY_NUMBER] ;
                this.mediumDogBadTotal = this.mediumDogBadTotal + dogNumbers[MID_DOG_CATEGORY_NUMBER];
                this.largeDogBadTotal = this.largeDogGoodTotal + dogNumbers[LARGE_DOG_CATEGORY_NUMBER];
            }
        }
    }
    public void updateReviewCount() {
        this.reviewCount += 1;
    }
    public void addDogsInfo(ReviewPostRequestDto request) {
        updateReviewCount();
        int[] dogNumbers = new int[TOTAL_DOG_CATEGORY_SIZE];
        dogNumbers[SMALL_DOG_CATEGORY_NUMBER] = request.getSmallDogNumber();
        dogNumbers[MID_DOG_CATEGORY_NUMBER] = request.getMediumDogNumber();
        dogNumbers[LARGE_DOG_CATEGORY_NUMBER] = request.getLargeDogNumber();
        countAndUpdateResult(dogNumbers, request.getRecommendStatus());
    }
}
