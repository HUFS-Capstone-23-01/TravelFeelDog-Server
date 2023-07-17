package travelfeeldog.aggregate.community.feed.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import travelfeeldog.aggregate.community.feed.model.FeedImages;
import travelfeeldog.aggregate.community.tag.model.Tag;
import travelfeeldog.aggregate.member.dto.MemberDtos.MemberResponse;
import travelfeeldog.aggregate.community.feed.model.Feed;
import travelfeeldog.aggregate.community.feed.model.FeedTag;

import java.time.LocalDate;
import java.util.ArrayList;
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
        private List<String> feedImageUrls = new ArrayList<>();
        private List<String> feedTags = new ArrayList<>();

        public FeedStaticResponseDto(Feed feed) {
            this.feedId = feed.getId();
            this.member = new MemberResponse(feed.getMember());
            this.likes = feed.getLikeCount();
            this.scraps = feed.getScrapCount();
            this.title = feed.getTitle();
            this.body = feed.getBody();
            this.createdDateTime = feed.getCreatedDateTime().toLocalDate();
            if(feed.getFeedImages().isEmpty()) {
                this.feedImageUrls.add("https://tavelfeeldog.s3.ap-northeast-2.amazonaws.com/feed/%EC%BB%A4%EB%AE%A4%EB%8B%88%ED%8B%B0%20%EA%B8%B0%EB%B3%B8.png");
            }
            else {
                this.feedImageUrls.addAll(feed.getFeedImages().stream().map(FeedImages::getFeedImageUrl).distinct().toList());
            }
            this.feedTags.addAll(feed.getFeedTags().stream().map(f -> f.getTag().getTagContent()).distinct().toList());
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
        private List<String> feedTagContents = new ArrayList<>();

        public FeedListResponseDto(Feed feed) {
            this.member = new MemberResponse(feed.getMember());
            this.feedId = feed.getId();
            this.likes = feed.getLikeCount();
            this.scraps = feed.getScrapCount();
            this.title = feed.getTitle();
            this.body = feed.getBody();
            this.createdDateTime = feed.getCreatedDateTime().toLocalDate();
            if (feed.getFeedImages().isEmpty()) {
                this.feedImagesUrl = "https://tavelfeeldog.s3.ap-northeast-2.amazonaws.com/feed/%EC%BB%A4%EB%AE%A4%EB%8B%88%ED%8B%B0%20%EA%B8%B0%EB%B3%B8.png";
            } else {
                this.feedImagesUrl = feed.getFeedImages().get(0).getFeedImageUrl();
            }
            List<Tag> Tags = feed.getFeedTags().stream().map(FeedTag::getTag).toList();
            if (!Tags.isEmpty()) {
                for (Tag Tag : Tags) {
                    feedTagContents.add(Tag.getTagContent());
                }
            }
        }
    }
}
