package travelfeeldog.domain.member.domain.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.ArrayList;
import java.util.List;

import java.util.Objects;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import org.springframework.util.Assert;
import travelfeeldog.domain.community.feedlike.model.FeedLike;
import travelfeeldog.domain.community.feed.model.Feed;
import travelfeeldog.domain.community.scrap.model.Scrap;
import travelfeeldog.domain.review.review.model.Review;
import travelfeeldog.global.common.domain.model.BaseTimeEntity;

@Getter
@Entity
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_nickname", unique = true)
    private String nickName;
    final private static Long NICK_NAME_MAX_LENGTH = 30L;

    @Column(name = "member_email", unique = true)
    private String email;
    final private static Long EMAIL_MAX_LENGTH = 320L;

    @Column(name = "member_level")
    private int level;

    @Column(name = "member_exp")
    private int exp;
    @ColumnDefault("'/base/baseLogo.png'")
    @Column(name = "member_image_url")
    private String imageUrl;

    @Column(name = "member_delete")
    private boolean delete;

    @Column(name = "member_block")
    private boolean block;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedLike> feedLikes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Scrap> scraps = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feed> feeds = new ArrayList<>();

    private final static Integer USER_MAX_EXP = 100;

    @Builder
    private Member(String nickName,
            String email,
            int level,
            int exp) {
        validateNickname(nickName);
        validateEmail(email);
        this.nickName = nickName;
        this.level = level;
        this.email = Objects.requireNonNull(email,
                "Email cannot be null"); // Initialize 'email' field
        this.exp = exp;
    }
    @Builder(builderClassName = "ByAccountBuilder", builderMethodName = "ByAccountBuilder")
    public Member(String nickName,
            String email,
            String imageUrl,
            Role role) {
        validateNickname(nickName);
        validateEmail(email);
        this.role = role;
        this.nickName = nickName;
        this.imageUrl = imageUrl ;
        this.email = Objects.requireNonNull(email,
                "Email cannot be null"); // Initialize 'email' field
    }

    public static Member register(String nickName,
            String email,
            int level,
            int exp) {
        return Member.builder()
                .nickName(nickName)
                .email(email)
                .level(level)
                .exp(exp)
                .build();
    }

    public void updateExpAndLevel(int addingExp) {
        int changedExp = this.exp + addingExp;
        if (changedExp / USER_MAX_EXP == 0) {
            this.exp = changedExp;
        } else {
            this.exp = changedExp % USER_MAX_EXP;
            this.level = this.level + 1;
        }
    }

    public void updateMemberNickName(String to) {
        Objects.requireNonNull(to);
        validateNickname(to);
        this.nickName = to;
    }
    public Member updateMemberNickNameAndImage(String nickName,String imageUrl) {
        this.nickName = nickName;
        this.imageUrl = imageUrl;
        return this;
    }
    public void blockMember() {
        this.block = true;
    }

    public void unblockMember() {
        this.block = false;
    }

    public void deleteMember() {
        this.delete = true;
    }
    public void setWriterId(Long id){
        this.id= id;
    }
    public String getRoleKey() {
        return this.role.getKey();
    }
    public void updateMemberImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private void validateNickname(String nickname) {
        Assert.isTrue(nickname.length() <= NICK_NAME_MAX_LENGTH, "닉네임 최대 길이를 초과했습니다.");
    }

    private void validateEmail(String email) {
        Assert.isTrue(email.length() <= EMAIL_MAX_LENGTH, "이메일 최대 길이를 초과했습니다.");
    }
}
