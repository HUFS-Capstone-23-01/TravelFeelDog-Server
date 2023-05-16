package travelfeeldog.domain.member.model;



import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import travelfeeldog.domain.feed.FeedLike.model.FeedLike;
import travelfeeldog.domain.feed.feed.model.Feed;
import travelfeeldog.domain.feed.scrap.model.Scrap;
import travelfeeldog.domain.review.review.model.Review;
import travelfeeldog.global.common.model.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_nickname", unique = true)
    private String nickName;

    @Column(name = "member_level")
    private int level;

    @Column(name = "member_exp")
    private int exp;

    @Column(name = "member_image_url")
    private String imageUrl;

    @Column(name = "member_token", unique = true)
    private String token;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedLike> feedLikes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Scrap> scraps = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feed> feeds = new ArrayList<>();

    private Member(String nickName,
                   int level,
                   int exp,
                   String imageUrl,
                   String token) {
        this.nickName = nickName;
        this.level = level;
        this.exp = exp;
        this.imageUrl = imageUrl;
        this.token = token;
    }

    public static Member create(String nickName,
                                int level,
                                int exp,
                                String imageUrl,
                                String token) {
        return new Member(nickName, level, exp, imageUrl, token);
    }

    //==연관관계 메소드==//
    public boolean updateExpAndLevel(int addingExp) {
        int changedExp = this.exp + addingExp;
        if (changedExp / 100 == 0) {
            this.exp = changedExp;
            return false;
        } else {
            this.exp = changedExp % 100;
            this.level = this.level + 1;
        }
        return true;
    }
    public void addFeedLike(FeedLike feedLike) {
        feedLikes.add(feedLike);
        feedLike.setMember(this);
    }
    public void addReview(Review review) {
        reviews.add(review);
        review.setMember(this);
    }
    public void addFeed(Feed feed) {
        feeds.add(feed);
        feed.setMember(this);
    }
}
