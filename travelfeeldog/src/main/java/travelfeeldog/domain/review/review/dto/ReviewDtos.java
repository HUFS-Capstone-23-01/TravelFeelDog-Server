package travelfeeldog.domain.review.review.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;
import travelfeeldog.domain.review.review.model.RecommendStatus;
import travelfeeldog.domain.review.review.model.Review;
import travelfeeldog.domain.review.review.model.ReviewImage;

public class ReviewDtos {
    @Data
    public static class UpdateReviewImageDto{
        private Long reviewId;
        private String imageUrl;
    }
    @Data
    public static class SingleDescriptionAndNickNameDto{
        private String additionalScript;
        private String nickName;
        public SingleDescriptionAndNickNameDto(Review review){
            this.additionalScript = review.getAdditionalScript();
            this.nickName = review.getMember().getNickName();
        }

    }
    @Data
    public static class ReviewPostRequestDto{
        private Long placeId;
        private String additionalScript;
        private RecommendStatus recommendStatus; // GOOD,IDK,BAD
        private int smallDogNumber;
        private int mediumDogNumber;
        private int largeDogNumber;
        private List<String> imageUrls ;
        private List<Long> goodKeyWordIds;
        private List<Long> badKeyWordIds;
        public ReviewPostRequestDto(Long placeId, String additionalScript, String recommendStatus,
                                    int smallDogNumber, int mediumDogNumber, int largeDogNumber,
                                    List<String> imageUrls, List<Long> goodKeyWordIds, List<Long> badKeyWordIds){
            this.placeId = placeId;
            this.additionalScript = additionalScript;
            this.recommendStatus = RecommendStatus.valueOf(recommendStatus);
            this.smallDogNumber = smallDogNumber;
            this.mediumDogNumber = mediumDogNumber;
            this.largeDogNumber = largeDogNumber;
            this.imageUrls = imageUrls != null ? imageUrls : new ArrayList<>();
            this.goodKeyWordIds = goodKeyWordIds != null ? goodKeyWordIds : new ArrayList<>();
            this.badKeyWordIds = badKeyWordIds != null ? badKeyWordIds : new ArrayList<>();
        }

    }
    @Data
    @NoArgsConstructor
    public static class ReviewPageResponseDto{
        private Long reviewId;
        private String memberNickname;
        private int memberLevel;
        private String memberImageUrl;
        private String recommendStatus;
        private LocalDateTime createdDateTime;
        private String additionalScript;
        private List<String> imageUrls ;

        public ReviewPageResponseDto(Review review) {
            reviewId = review.getId();
            memberImageUrl = review.getMember().getImageUrl();
            memberLevel = review.getMember().getLevel();
            memberNickname = review.getMember().getNickName();
            createdDateTime = review.getCreatedDateTime();
            recommendStatus = review.getRecommendStatus().toString();
            additionalScript =review.getAdditionalScript();
            imageUrls = review.getReviewImages().stream().map(ReviewImage::getImageUrl).collect(Collectors.toList());
        }
    }
    @Data
    @NoArgsConstructor
    public static class ReviewMemberPageResponseDto{
        private Long id;
        private String placeName;
        private String recommendStatus;
        private LocalDateTime createdDateTime;
        private String additionalScript;
        private List<String> imageUrls ;
        public ReviewMemberPageResponseDto(Review review) {
            id = review.getId();
            placeName = review.getPlace().getName();
            createdDateTime = review.getCreatedDateTime();
            recommendStatus = review.getRecommendStatus().toString();
            additionalScript =review.getAdditionalScript();
            imageUrls = review.getReviewImages().stream().map(ReviewImage::getImageUrl).collect(Collectors.toList());
        }
    }
}
