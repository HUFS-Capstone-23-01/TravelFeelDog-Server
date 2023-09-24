package travelfeeldog.community.feed.domain.model;

import jakarta.persistence.CascadeType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.util.Assert;
import travelfeeldog.global.common.domain.model.BaseTimeEntity;




@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class FeedImages extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_images_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "feed_id")
    private Feed feed;

    @ColumnDefault("'/base/feed.JPG'")
    @Column(name = "feed_image_url")
    private String feedImageUrl;

    public FeedImages(Feed feed, String feedImageUrl) {
        this.feed = feed;
        feedImageUrlValidate(feedImageUrl);
        this.feedImageUrl = feedImageUrl;
    }

    private void feedImageUrlValidate(String feedImageUrl) {
        Assert.isTrue(feedImageUrl.contains(".jpg"), "확장자가 잘못 되었습니다.");
    }

    protected void setFeed(Feed feed){
        this.feed = feed;
    }
    public static FeedImages create(Feed feed, String feedImageUrl) { return new FeedImages(feed, feedImageUrl); }
}
