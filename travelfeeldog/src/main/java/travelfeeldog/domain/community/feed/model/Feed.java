package travelfeeldog.domain.community.feed.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.ColumnDefault;
import travelfeeldog.domain.community.feedlike.model.FeedLike;
import travelfeeldog.domain.community.comment.model.Comment;
import travelfeeldog.domain.community.scrap.model.Scrap;
import travelfeeldog.domain.member.domain.model.Member;
import travelfeeldog.global.common.domain.model.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Feed extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "feed_like_count")
    @ColumnDefault("0")
    private int likeCount;

    @Column(name = "feed_scrap_count")
    @ColumnDefault("0")
    private int scrapCount;

    @Column(name = "feed_title")
    private String title;

    @Column(name = "feed_body")
    private String body;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FeedImages> feedImages = new HashSet<>();

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FeedTag> feedTags = new HashSet<>();

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Scrap> feedScraps = new ArrayList<>();

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedLike> feedLikes = new ArrayList<>();

    @Builder
    private Feed(Member member, int likeCount, int scrapCount, String title, String body) {
        this.member = Objects.requireNonNull(member);
        this.likeCount = likeCount;
        this.scrapCount = scrapCount;
        this.title = Objects.requireNonNull(title);
        this.body = Objects.requireNonNull(body);
    }

    public static Feed create(Member member,
                              String title,
                              String body) {
        return  Feed.builder()
            .member(member)
            .title(title)
            .body(body)
            .likeCount(0)
            .scrapCount(0)
            .build();
    }
    public void updateFeedLikeCountPlus(boolean add) {
        if(add) {
            this.likeCount += 1;
        }
        else {
            this.likeCount -= 1;
        }
    }

    public void updateScrapCountPlus(boolean add) {
        if(add) {
            this.scrapCount += 1;
        }
        else {
            this.scrapCount -= 1;
        }
    }
    public void setFeedImages(List<String> feedImageUrls) {
        if(feedImageUrls.isEmpty()) return;
        feedImageUrls.forEach(this::addFeedImage);
    }

    public void setTags(List<Tag> tags) {
        if(tags.isEmpty()) return;
        tags.forEach(this::addTag);
    }

    public void addFeedImage(String feedImageUrl) {
        FeedImages feedImage = new FeedImages(this, feedImageUrl);
        feedImage.setFeed(this);
        this.feedImages.add(feedImage);
    }

    public void addTag(Tag tag) {
        FeedTag feedTag = new FeedTag();
        feedTag.setTagAndFeed(tag, this);
    }

    public Set<String> getFeedImagesUrls() {
        if (this.feedImages.isEmpty()) {
            return Collections.singleton("/feed/Base.png");
        }
        return this.feedImages.stream()
            .map(FeedImages::getFeedImageUrl)
            .collect(Collectors.toSet());
    }
    public Set<String> getFeedTagsContent() {
        return this.feedTags.stream()
            .map(f -> f.getTag().getTagContent())
            .collect(Collectors.toSet());
    }
    public String getFeedImageUrl() {
        if (this.feedImages.isEmpty()) {
            return ("/feed/Base.png");
        }
        return this.feedImages.stream()
            .map(FeedImages::getFeedImageUrl)
            .toList().get(0);
    }

}
