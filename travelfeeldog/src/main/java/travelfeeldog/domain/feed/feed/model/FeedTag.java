package travelfeeldog.domain.feed.feed.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import travelfeeldog.domain.feed.tag.model.Tag;
import travelfeeldog.global.common.domain.model.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
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

    public static FeedTag FeedTag(Feed feed, Tag tag){
        return new FeedTag(feed, tag);
    }

    public void setTagAndFeed(Tag tag, Feed feed) {
        this.tag = tag;
        this.feed = feed;
        tag.getFeedTags().add(this);
        feed.getFeedTags().add(this);
    }
}
