package travelfeeldog.community.feed.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import travelfeeldog.global.common.domain.model.BaseTimeEntity;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedTag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feed feed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    private FeedTag(Feed feed, Tag tag) {
        this.feed = feed;
        this.tag = tag;
    }

    public static FeedTag createFeedTag(Feed feed, Tag tag){
        return new FeedTag(feed, tag);
    }

    public void setTagAndFeed(Tag tag, Feed feed) {
        this.tag = tag;
        this.feed = feed;
        tag.getFeedTags().add(this);
        feed.getFeedTags().add(this);
    }
}
