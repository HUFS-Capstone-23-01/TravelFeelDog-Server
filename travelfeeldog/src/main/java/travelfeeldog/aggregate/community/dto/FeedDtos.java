package travelfeeldog.aggregate.community.dto;

import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import travelfeeldog.aggregate.community.feed.domain.model.Feed;
import travelfeeldog.aggregate.member.dto.MemberDtos.MemberResponse;

import java.time.LocalDate;
import java.util.List;


public class FeedDtos {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FeedPostRequestDto {
        private String memberToken;
        private String title;
        private String body;
        private List<String> feedImageUrls;
        private List<String> feedTags;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FeedStaticResponseDto {
        private MemberResponse member;
        private Long feedId;
        private String title;
        private String body;
        private LocalDate createdDateTime;
        private int likes;
        private Long feedLikeId = 0L;
        private int scraps;
        private Long feedScrapId = 0L;
        private Set<String> feedImageUrls ;
        private Set<String> feedTags = new HashSet<>();

        public FeedStaticResponseDto(Feed feed) {
            this.feedId = feed.getId();
            this.member = new MemberResponse(feed.getMember());
            this.likes = feed.getLikeCount();
            this.scraps = feed.getScrapCount();
            this.title = feed.getTitle();
            this.body = feed.getBody();
            this.createdDateTime = feed.getCreatedDateTime().toLocalDate();
            this.feedImageUrls = feed.getFeedImagesUrls();
            this.feedTags = feed.getFeedTagsContent();
        }
    }
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FeedListResponseDto {
        private MemberResponse member;
        private Long feedId;
        private int likes;
        private int scraps;
        private String title;
        private String body;
        private LocalDate createdDateTime;
        private String feedImagesUrl;
        private List<String> feedTagContents;

        public FeedListResponseDto(Feed feed) {
            this.member = new MemberResponse(feed.getMember());
            this.feedId = feed.getId();
            this.likes = feed.getLikeCount();
            this.scraps = feed.getScrapCount();
            this.title = feed.getTitle();
            this.body = feed.getBody();
            this.createdDateTime = feed.getCreatedDateTime().toLocalDate();
            this.feedImagesUrl = feed.getFeedImageUrl();
            this.feedTagContents =feed.getFeedTagsContent().stream().toList();
        }
    }
}
