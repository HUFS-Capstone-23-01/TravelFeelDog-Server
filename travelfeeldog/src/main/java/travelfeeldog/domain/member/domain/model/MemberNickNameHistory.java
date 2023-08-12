package travelfeeldog.domain.member.domain.model;

import lombok.Builder;
import lombok.Getter;
import travelfeeldog.global.common.domain.model.BaseTimeEntity;

@Getter
public class MemberNickNameHistory extends BaseTimeEntity {

    private final Long id;
    private final Long memberId;
    private final String nickName;
    @Builder
    public MemberNickNameHistory(Long id,Long memberId, String nickName) {
        this.id = id;
        this.memberId = memberId;
        this.nickName = nickName;
    }
}
