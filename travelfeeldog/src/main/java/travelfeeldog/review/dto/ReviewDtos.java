package travelfeeldog.review.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import travelfeeldog.review.reviewpost.domain.model.RecommendStatus;
import travelfeeldog.review.reviewpost.domain.model.Review;

public class ReviewDtos {
    public record UpdateReviewImageDto(Long reviewId, String imageUrl) {
    }

    @Getter
    public static class SingleDescriptionAndNickNameDto {
        private final String additionalScript;
        private final String nickName;

        public SingleDescriptionAndNickNameDto(final Review review) {
            this.additionalScript = review.getAdditionalScript();
            this.nickName = review.getReviewOwnerNickNameForResponse();
        }

    }

    @Getter
    public static class ReviewPostRequestDto {
        private final Long placeId;
        private final String additionalScript;
        private final RecommendStatus recommendStatus;
        private final int smallDogNumber;
        private final int mediumDogNumber;
        private final int largeDogNumber;
        private final List<String> imageUrls;
        private final List<Long> goodKeyWordIds;
        private final List<Long> badKeyWordIds;

        public ReviewPostRequestDto(Long placeId, String additionalScript, String recommendStatus,
                                    int smallDogNumber, int mediumDogNumber, int largeDogNumber,
                                    List<String> imageUrls, List<Long> goodKeyWordIds, List<Long> badKeyWordIds) {
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

    @Getter
    public static class ReviewPageResponseDto {
        private final Long reviewId;
        private final String memberNickname;
        private final int memberLevel;
        private final String memberImageUrl;
        private final String recommendStatus;
        private final LocalDateTime createdDateTime;
        private final String additionalScript;
        private final List<String> imageUrls;

        public ReviewPageResponseDto(final Review review) {
            reviewId = review.getId();
            memberImageUrl = review.getReviewOwnerImageUrlForResponse();
            memberLevel = review.getReviewOwnerLevelForResponse();
            memberNickname = review.getReviewOwnerNickNameForResponse();
            createdDateTime = review.getCreatedDateTime();
            recommendStatus = review.getRecommendStatus().toString();
            additionalScript = review.getAdditionalScript();
            imageUrls = review.getAllReviewImages();
        }
    }

    @Getter
    public static class ReviewMemberPageResponseDto {
        private final Long id;
        private final String placeName;
        private final String recommendStatus;
        private final LocalDateTime createdDateTime;
        private final String additionalScript;
        private final List<String> imageUrls;

        public ReviewMemberPageResponseDto(Review review) {
            id = review.getId();
            placeName = review.getPlaceNameOfReviewForResponse();
            createdDateTime = review.getCreatedDateTime();
            recommendStatus = review.getRecommendStatus().toString();
            additionalScript = review.getAdditionalScript();
            imageUrls = review.getAllReviewImages();
        }
    }
}
