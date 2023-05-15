package travelfeeldog.domain.feed.feed.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import travelfeeldog.domain.feed.comment.model.Comment;
import travelfeeldog.domain.member.model.Member;
import travelfeeldog.domain.feed.tag.model.Tag;
import travelfeeldog.global.common.model.BaseTimeEntity;

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

    @Column(name = "feed_like")
    private int likes;

    @Column(name = "feed_scrap")
    private int scraps;

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

    private Feed(Member member, int likes, int scraps, String title, String body) {
        this.member = member;
        this.likes = likes;
        this.scraps = scraps;
        this.title = title;
        this.body = body;
    }

    public static Feed create(Member member,
                              int likes,
                              int scraps,
                              String title,
                              String body) {
        return  new Feed(member, likes, scraps, title, body);
    }

    public static Feed create(Member member,
                              List<String> feedImageUrls,
                              int likes,
                              int scraps,
                              String title,
                              String body) {
        Feed feed =  new Feed(member, likes, scraps, title, body);
        for(String feedImageUrl : feedImageUrls) { feed.addFeedImage(feedImageUrl); }
        return feed;
    }

    public static Feed create(Member member,
                              List<String> feedImageUrls,
                              int likes,
                              int scraps,
                              String title,
                              String body,
                              List<Tag> tags) {
        Feed feed =  new Feed(member, likes, scraps, title, body);
        for(String feedImageUrl : feedImageUrls) { feed.addFeedImage(feedImageUrl); }
        for(Tag tag : tags) { feed.addTag(tag); }
        return feed;
    }

    public static Feed create(Member member,
                              int likes,
                              int scraps,
                              String title,
                              String body,
                              List<Tag> tags) {
        Feed feed =  new Feed(member, likes, scraps, title, body);
        for(Tag tag : tags) { feed.addTag(tag); }
        return feed;
    }

    public void addLikes() {
        this.likes = this.likes + 1;
    }

    public void addScraps() {
        this.scraps = this.scraps + 1;
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
        this.feedTags.add(feedTag);
    }

    public void addComment(Comment comment) {
        comment.setFeed(this);
        this.comments.add(comment);
    }

}
