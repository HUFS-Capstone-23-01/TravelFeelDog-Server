package travelfeeldog.domain.member.model;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import travelfeeldog.domain.FeedLike.model.FeedLike;
import travelfeeldog.domain.review.model.Review;
import travelfeeldog.domain.scrab.model.Scrab;
import travelfeeldog.global.common.model.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", length = 64)
    private Long id;

    @Column(name = "member_nickname", unique = true, length = 100)
    private String nickName;

    @Column(name = "member_level", length = 100)
    private int level;

    @Column(name = "member_exp", length = 100)
    private int exp;

    @Column(name = "member_image_url", length = 100)
    private String imageUrl;

    @Column(name = "member_token", unique = true, length = 100)
    private String token;

    @OneToMany(mappedBy = "feed_like_id")
    private List<FeedLike> feedLikes = new ArrayList<>();

    @OneToMany(mappedBy = "scrab_id")
    private List<Scrab> scrabs = new ArrayList<>();

    @OneToMany(mappedBy = "review_id")
    private List<Review> reviews = new ArrayList<>();

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
        if (changedExp / 40 == 0) {
            this.exp = changedExp;
            return false;
        } else {
            this.exp = changedExp % 40;
            this.level = this.exp + 1;
        }
        return true;
    }
}
