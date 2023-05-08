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
        String nickName;
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
    public static class MemberGetIdDto { String id; }

    @Data
    @NonNull
    @NoArgsConstructor
    public static class MemberGetNickDto { String nickName; }

    @Data
    @NonNull
    @NoArgsConstructor
    public static class MemberPutExpDto {
        String addingValue;
        String firebaseToken;
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MemberResponse {
        private Long id;
        private String nickName;
        private int level;
        private int exp;
        private String imageUrl;
        private String token;

        public MemberResponse(Member member) {
            this(
                    member.getId(),
                    member.getNickName(),
                    member.getLevel(),
                    member.getExp(),
                    member.getImageUrl(),
                    member.getToken()
            );
        }

        public MemberResponse(Long id, String nickName, int level, int exp, String imageUrl, String token) {
            this.id = id;
            this.nickName = nickName;
            this.level = level;
            this.exp = exp;
            this.imageUrl = imageUrl;
            this.token = token;
        }
    }
}
