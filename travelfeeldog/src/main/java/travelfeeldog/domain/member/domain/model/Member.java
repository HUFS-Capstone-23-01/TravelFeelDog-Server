package travelfeeldog.domain.member.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import travelfeeldog.domain.community.FeedLike.model.FeedLike;
import travelfeeldog.domain.community.feed.model.Feed;
import travelfeeldog.domain.community.scrap.model.Scrap;
import travelfeeldog.domain.review.review.model.Review;
import travelfeeldog.global.common.domain.model.BaseTimeEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
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
    @ColumnDefault("'/base/baseLogo.png'")
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
    @Builder
    private Member(String nickName,
                   int level,
                   int exp,
                   String token) {
        this.nickName = nickName;
        this.level = level;
        this.exp = exp;
        this.token = token;
    }

    public static Member create(String nickName,
                                int level,
                                int exp,
                                String token) {
        return Member.builder().
            nickName(nickName)
            .level(level)
            .exp(exp)
            .token(token)
            .build();
    }
    public void updateExpAndLevel(int addingExp) {
        int changedExp = this.exp + addingExp;
        if (changedExp / 100 == 0) {
            this.exp = changedExp;
        } else {
            this.exp = changedExp % 100;
            this.level = this.level + 1;
        }
    }
    public void updateMemberNickName(String nickName){
        this.nickName = nickName;
    }

    public void updateMemberImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
