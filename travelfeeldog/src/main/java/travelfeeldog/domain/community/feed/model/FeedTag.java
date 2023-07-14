package travelfeeldog.domain.community.feed.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
<<<<<<< HEAD:travelfeeldog/src/main/java/travelfeeldog/domain/feed/feed/model/FeedTag.java
import travelfeeldog.domain.feed.tag.model.Tag;
import travelfeeldog.global.common.domain.model.BaseTimeEntity;
=======
import travelfeeldog.domain.community.tag.model.Tag;
import travelfeeldog.global.common.model.BaseTimeEntity;
>>>>>>> develop:travelfeeldog/src/main/java/travelfeeldog/domain/community/feed/model/FeedTag.java

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
