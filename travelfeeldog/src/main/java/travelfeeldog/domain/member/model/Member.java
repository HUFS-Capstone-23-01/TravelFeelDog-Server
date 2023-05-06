package travelfeeldog.domain.member.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import travelfeeldog.global.common.model.BaseTimeEntity;

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
}
