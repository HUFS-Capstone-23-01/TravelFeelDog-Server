package travelfeeldog.review.domain.review.model;

import static jakarta.persistence.FetchType.LAZY;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import travelfeeldog.member.domain.model.Member;
import travelfeeldog.review.dto.ReviewDtos.ReviewPostRequestDto;
import travelfeeldog.review.domain.reviewkeyword.model.ReviewGoodKeyWord;
import travelfeeldog.place.domain.place.model.Place;
import travelfeeldog.global.common.domain.basetime.BaseTimeEntity;

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

    @OneToMany (mappedBy = "review",cascade = CascadeType.ALL, orphanRemoval = true, fetch = LAZY)
    private List<ReviewImage> reviewImages = new ArrayList<>();

    protected Review() {

    }

    public static Review AddReview(Member member, Place place, ReviewPostRequestDto request) {
        return new Review(member,place, request);
    }
    private Review(Member member, Place place, ReviewPostRequestDto request) {
        this(member, place, request.getAdditionalScript(),
            request.getRecommendStatus(),
            request.getSmallDogNumber(), request.getMediumDogNumber(), request.getLargeDogNumber());
        this.reviewImages = addReviewImages(request.getImageUrls());
    }

    private Review(Member member, Place place, String additionalScript, RecommendStatus recommendStatus,
        int smallDogNumber, int mediumDogNumber, int largeDogNumber) {
        this.member = member;
        this.place = place;
        this.additionalScript = additionalScript;
        this.recommendStatus = recommendStatus;
        this.smallDogNumber = smallDogNumber;
        this.mediumDogNumber = mediumDogNumber;
        this.largeDogNumber = largeDogNumber;
    }

    private List<ReviewImage> addReviewImages(List<String> imageUrls){
        return imageUrls.stream()
                .map(imageUrl -> new ReviewImage(this, imageUrl))
            .toList();
    }
    public String getReviewOwnerNickNameForResponse() {
        return this.member.getNickName();
    }
    public int getReviewOwnerLevelForResponse(){
        return this.member.getLevel();
    }
    public String getReviewOwnerImageUrlForResponse() {
        return this.member.getImageUrl();
    }
    public String getPlaceNameOfReviewForResponse() {
        return this.place.getName();
    }
    public List<String> getAllReviewImages() {
        return this.getReviewImages().stream()
            .map(ReviewImage::getImageUrl)
            .collect(Collectors.toList());
    }
}
