package travelfeeldog.domain.member.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travelfeeldog.global.common.domain.model.BaseTimeEntity;

@Getter
@NoArgsConstructor
@Table(name = "member_nick_history")
@Entity
public class MemberNicknameHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private String nickName;
    @Builder
    public MemberNicknameHistory(Long id,Long memberId, String nickName) {
        this.id = id;
        this.memberId = memberId;
        this.nickName = nickName;
    }
}
