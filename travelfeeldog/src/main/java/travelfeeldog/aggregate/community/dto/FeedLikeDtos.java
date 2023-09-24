package travelfeeldog.aggregate.community.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import travelfeeldog.aggregate.community.feedlike.domain.model.FeedLike;

public class FeedLikeDtos {
    
    @Data
    @NoArgsConstructor
    public static class FeedLikeRequestDto {
        private Long feedId;
    }
    @Data
    public static class FeedLikesByMemberResponseDto {
        private Long feedLikeId;
        private Long feedId;
        private String title;
        private String body;
        public FeedLikesByMemberResponseDto(FeedLike feedLike){
            this.feedLikeId = feedLike.getId();
            this.feedId = feedLike.getFeed().getId();
            this.title = feedLike.getFeed().getTitle();
            this.body = feedLike.getFeed().getBody();
        }
    }
}
