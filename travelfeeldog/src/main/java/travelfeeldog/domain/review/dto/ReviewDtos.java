package travelfeeldog.domain.review.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import travelfeeldog.domain.member.model.Member;
import travelfeeldog.domain.review.model.RecommendStatus;
import travelfeeldog.domain.review.model.Review;

public class ReviewDtos {
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
        private String memberNickname;
        private int memberLevel;
        private String memberImageUrl;
        private String recommendStatus;
        private LocalDateTime createdDateTime;
        private String additionalScript;

        public ReviewPageResponseDto(Review review) {
            memberImageUrl = review.getMember().getMemberImageUrl();
            memberLevel = review.getMember().getMemberLevel();
            memberNickname = review.getMember().getMemberNickName();
            createdDateTime = review.getCreatedDateTime();
            recommendStatus = review.getRecommendStatus().toString();
            additionalScript =review.getAdditionalScript();
        }
    }
    @Data
    @NoArgsConstructor
    public static class ReviewMemberPageResponseDto{
        private String placeName;
        private String recommendStatus;
        private LocalDateTime createdDateTime;
        private String additionalScript;
        public ReviewMemberPageResponseDto(Review review) {
            placeName = review.getPlace().getName();
            createdDateTime = review.getCreatedDateTime();
            recommendStatus = review.getRecommendStatus().toString();
            additionalScript =review.getAdditionalScript();
        }
    }
}
