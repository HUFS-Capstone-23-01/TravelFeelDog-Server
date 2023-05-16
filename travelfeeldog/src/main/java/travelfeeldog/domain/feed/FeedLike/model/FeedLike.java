package travelfeeldog.domain.feed.FeedLike.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import travelfeeldog.domain.feed.feed.model.Feed;
import travelfeeldog.domain.member.model.Member;
import travelfeeldog.global.common.model.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedLike extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feed feed;

    private FeedLike(Member member, Feed feed) {
        this.member = member;
        this.feed = feed;
    }

    public static FeedLike FeedLike(Member member, Feed feed) {
        return new FeedLike(member, feed);
    }

    public void setMemberAndFeed(Member member, Feed feed) {
        this.member = member;
        this.feed = feed;
        member.getFeedLikes().add(this);
        feed.getFeedLikes().add(this);
    }
}
