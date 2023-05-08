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
    private Long memberId;

    @Column(name = "member_nickname", unique = true, length = 100)
    private String memberNickName;

    @Column(name = "member_level", length = 100)
    private int memberLevel;

    @Column(name = "member_exp", length = 100)
    private int memberExp;

    @Column(name = "member_image_url", length = 100)
    private String memberImageUrl;

    @Column(name = "member_token", unique = true, length = 100)
    private String memberToken;

    private Member(String memberNickName,
                   int memberLevel,
                   int memberExp,
                   String memberImageUrl,
                   String memberToken) {
        this.memberNickName = memberNickName;
        this.memberLevel = memberLevel;
        this.memberExp = memberExp;
        this.memberImageUrl = memberImageUrl;
        this.memberToken = memberToken;
    }

    public static Member create(String memberNickName,
                                int memberLevel,
                                int memberExp,
                                String memberImageUrl,
                                String memberToken) {
        return new Member(memberNickName, memberLevel, memberExp, memberImageUrl, memberToken);
    }

    //==연관관계 메소드==//
    public boolean updateExpAndLevel(int addingExp) {
        int changedExp = this.memberExp + addingExp;
        if (changedExp / 40 == 0) {
            this.memberExp = changedExp;
            return false;
        } else {
            this.memberExp = changedExp % 40;
            this.memberLevel = this.memberExp + 1;
        }
        return true;
    }
}
