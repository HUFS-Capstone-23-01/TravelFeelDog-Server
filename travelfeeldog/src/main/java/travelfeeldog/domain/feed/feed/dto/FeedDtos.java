package travelfeeldog.domain.feed.feed.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travelfeeldog.domain.feed.feed.model.Feed;
import travelfeeldog.domain.feed.feed.model.FeedImages;
import travelfeeldog.domain.feed.feed.model.FeedTag;
import travelfeeldog.domain.member.dto.MemberDtos.MemberResponse;
import travelfeeldog.domain.feed.tag.model.Tag;

import java.util.ArrayList;
import java.util.List;


public class FeedDtos {
    @Getter
    public static class FeedCollectByMemberDetailResponseDto {
        private final Long id;
        private final String title;
        public FeedCollectByMemberDetailResponseDto(Feed feed){
            this.id=feed.getId();
            this.title = feed.getTitle();
        }
    }
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FeedPostRequestDto {
        private String memberToken;
        private String title;
        private String body;
        //private List<String> feedImagesUrls = new ArrayList<>();
        //private List<String> feedTags = new ArrayList<>();
    }
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FeedStaticResponseDto {
        private Long id;
        private String writerToken;
        private int likes;
        private int scraps;
        private String title;
        private String body;
        private List<String> feedImages = new ArrayList<>();
        private List<String> feedTags = new ArrayList<>();

        public FeedStaticResponseDto(Feed feed) {
            this.id = feed.getId();
            this.writerToken = feed.getMember().getToken();
            this.likes = feed.getLikes();
            this.scraps = feed.getScraps();
            this.title = feed.getTitle();
            this.body = feed.getBody();
            this.feedImages.addAll(feed.getFeedImages().stream().map(FeedImages::getFeedImageUrl).distinct().toList());
            this.feedTags.addAll(feed.getFeedTags().stream().map(f -> f.getTag().getTagContent()).distinct().toList());
        }
    }
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FeedListResponseDto {
        private MemberResponse member;
        private Long id;
        private int likes;
        private int scraps;
        private String title;
        private String body;
        private String feedImagesUrl;
        private List<String> feedTagContents = new ArrayList<>();

        public FeedListResponseDto(Feed feed) {
            this.member = new MemberResponse(feed.getMember());
            this.id = feed.getId();
            this.likes = feed.getLikes();
            this.scraps = feed.getScraps();
            this.title = feed.getTitle();
            this.body = feed.getBody();
            if (feed.getFeedImages().isEmpty()) {
                this.feedImagesUrl = "baseUrl";
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
