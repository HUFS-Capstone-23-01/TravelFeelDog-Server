package travelfeeldog.aggregate.community.feed.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import travelfeeldog.aggregate.community.FeedLike.model.FeedLike;
import travelfeeldog.aggregate.community.comment.model.Comment;
import travelfeeldog.aggregate.community.scrap.model.Scrap;
import travelfeeldog.aggregate.community.tag.model.Tag;
import travelfeeldog.aggregate.member.domain.model.Member;
import travelfeeldog.global.common.domain.model.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private List<FeedImages> feedImages = new ArrayList<>();

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedTag> feedTags = new ArrayList<>();

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Scrap> feedScraps = new ArrayList<>();

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedLike> feedLikes = new ArrayList<>();

    private Feed(Member member, int likeCount, int scrapCount, String title, String body) {
        this.member = member;
        this.likeCount = likeCount;
        this.scrapCount = scrapCount;
        this.title = title;
        this.body = body;
    }

    public static Feed create(Member member,
                              int likeCount,
                              int scrapCount,
                              String title,
                              String body) {
        return  new Feed(member, likeCount, scrapCount, title, body);
    }

    public static Feed create(Member member,
                              List<String> feedImageUrls,
                              int likeCount,
                              int scrapCount,
                              String title,
                              String body) {
        Feed feed =  new Feed(member, likeCount, scrapCount, title, body);
        for(String feedImageUrl : feedImageUrls) { feed.addFeedImage(feedImageUrl); }
        return feed;
    }

    public static Feed create(Member member,
                              List<String> feedImageUrls,
                              int likeCount,
                              int scrapCount,
                              String title,
                              String body,
                              List<Tag> tags) {
        Feed feed =  new Feed(member, likeCount, scrapCount, title, body);
        for(String feedImageUrl : feedImageUrls) { feed.addFeedImage(feedImageUrl); }
        for(Tag tag : tags) { feed.addTag(tag); }
        return feed;
    }

    public static Feed create(Member member,
                              int likeCount,
                              int scrapCount,
                              String title,
                              String body,
                              List<Tag> tags) {
        Feed feed =  new Feed(member, likeCount, scrapCount, title, body);
        for(Tag tag : tags) { feed.addTag(tag); }
        return feed;
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

    //==연관관계 메소드==//
    public void addFeedImage(String feedImageUrl) {
        FeedImages feedImage = new FeedImages(this, feedImageUrl);
        feedImage.setFeed(this);
        this.feedImages.add(feedImage);
    }

    public void addTag(Tag tag) {
        FeedTag feedTag = new FeedTag();
        feedTag.setTagAndFeed(tag, this);
    }

}
