package travelfeeldog.domain.member.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travelfeeldog.global.common.domain.model.BaseTimeEntity;

@Getter
@NoArgsConstructor
@Table(name = "member_nick_history")
@Entity
public class MemberNickNameHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private String nickName;
    @Builder
    public MemberNickNameHistory(Long id,Long memberId, String nickName) {
        this.id = id;
        this.memberId = memberId;
        this.nickName = nickName;
    }
}
