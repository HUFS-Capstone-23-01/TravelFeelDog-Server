package travelfeeldog.domain.review.review.model;

import static javax.persistence.FetchType.LAZY;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import travelfeeldog.domain.member.domain.model.Member;
import travelfeeldog.domain.place.place.model.Place;
import travelfeeldog.domain.review.review.dto.ReviewDtos.ReviewPostRequestDto;
import travelfeeldog.domain.review.reviewkeyword.model.ReviewBadKeyWord;
import travelfeeldog.domain.review.reviewkeyword.model.ReviewGoodKeyWord;
import travelfeeldog.global.common.domain.model.BaseTimeEntity;

@Setter
@Getter
@Entity
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_id")
    private Long id;

    @ColumnDefault("0")
    @Column(name="small_dog_number")
    private int smallDogNumber;
    @ColumnDefault("0")
    @Column(name="medium_dog_number")
    private int mediumDogNumber;
    @ColumnDefault("0")
    @Column(name="larger_dog_number")
    private int largeDogNumber;

    @Column(name="review_additional_script")
    private String additionalScript;
    @Enumerated(EnumType.STRING)
    private RecommendStatus recommendStatus; //GOOD,IDK,BAD
    @ManyToOne(fetch = LAZY,cascade= CascadeType.PERSIST)
    @JoinColumn(name="place_id")
    private Place place;
    @ManyToOne(fetch = LAZY,cascade= CascadeType.PERSIST)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany (mappedBy = "review",cascade = CascadeType.PERSIST)
    private List<ReviewGoodKeyWord> reviewGoodKeyWords = new ArrayList<>();
    @OneToMany (mappedBy = "review",cascade = CascadeType.PERSIST)
    private List<ReviewBadKeyWord> reviewBadKeyWords = new ArrayList<>();
    @OneToMany (mappedBy = "review",cascade = CascadeType.ALL, orphanRemoval = true, fetch = LAZY)
    private List<ReviewImage> reviewImages = new ArrayList<>();
    protected Review() {

    }
    public Review(Member member, Place place, ReviewPostRequestDto request){
        this.member = member;
        this.place = place;
        this.additionalScript = request.getAdditionalScript();
        this.recommendStatus = request.getRecommendStatus();
        this.smallDogNumber = request.getSmallDogNumber();
        this.mediumDogNumber = request.getMediumDogNumber();
        this.largeDogNumber = request.getLargeDogNumber();
        this.reviewImages = addReviewImages(request.getImageUrls());
    }
    private List<ReviewImage> addReviewImages(List<String> imageUrls){
        return imageUrls.stream()
                .map(imageUrl -> new ReviewImage(this, imageUrl))
                .collect(Collectors.toList());
    }
    public String getReviewOwnerNickName() {
        return this.member.getNickName();
    }
    public int getReviewOwnerLevel(){
        return this.member.getLevel();
    }
    public String getReviewOwnerImageUrl() {
        return this.member.getImageUrl();
    }
    public String getPlaceNameOfReview() {
        return this.place.getName();
    }
    public List<String> getAllReviewImages() {
        return this.getReviewImages().stream()
            .map(ReviewImage::getImageUrl)
            .collect(Collectors.toList());
    }
}
