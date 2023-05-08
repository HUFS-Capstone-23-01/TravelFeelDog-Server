package travelfeeldog.domain.member.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import travelfeeldog.domain.member.model.Member;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDtos {

    @Data
    @NonNull
    @NoArgsConstructor
    public static class MemberPostRequestDto {
        String memberNickName;
        String memberImageUrl;
        String firebaseToken;
    }

    @Data
    @NonNull
    @NoArgsConstructor
    public static class MemberPutNickNameDto {
        String nickName;
        String firebaseToken;
    }

    @Data
    @NonNull
    @NoArgsConstructor
    public static class MemberGetIdDto { Long memberId; }

    @Data
    @NonNull
    @NoArgsConstructor
    public static class MemberPutExpDto {
        int addingValue;
        String firebaseToken;
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MemberResponse {
        private Long memberId;
        private String memberNickName;
        private int memberLevel;
        private int memberExp;
        private String memberImageUrl;
        private String memberToken;

        public MemberResponse(Member member) {
            this(
                    member.getMemberId(),
                    member.getMemberNickName(),
                    member.getMemberLevel(),
                    member.getMemberExp(),
                    member.getMemberImageUrl(),
                    member.getMemberToken()
            );
        }

        public MemberResponse(Long memberId, String memberNickName, int memberLevel, int memberExp, String memberImageUrl, String memberToken) {
            this.memberId = memberId;
            this.memberNickName = memberNickName;
            this.memberLevel = memberLevel;
            this.memberExp = memberExp;
            this.memberImageUrl = memberImageUrl;
            this.memberToken = memberToken;
        }
    }
}
