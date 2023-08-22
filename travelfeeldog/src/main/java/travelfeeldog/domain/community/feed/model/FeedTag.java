package travelfeeldog.domain.community.feed.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
