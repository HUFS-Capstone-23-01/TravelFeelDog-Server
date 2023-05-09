package travelfeeldog.domain.member.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import travelfeeldog.domain.member.model.Member;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDtos {

    @Data
    @NoArgsConstructor
    public static class MemberPostRequestDto {
        @NotBlank(message = "NickName value is Empty or just Blank")
        String nickName;
        @NotBlank(message = "Token value is Empty or just Blank")
        String firebaseToken;
    }

    @Data
    @NoArgsConstructor
    public static class MemberPutNickNameDto {
        @NotBlank(message = "NickName value is Empty or just Blank")
        String nickName;
        @NotBlank(message = "Token value is Empty or just Blank")
        String firebaseToken;
    }

    @Data
    @NotBlank(message = "Id value for found is Empty or just Blank")
    @NoArgsConstructor
    public static class MemberGetIdDto { String id; }

    @Data
    @NotBlank(message = "NickName value is Empty or just Blank")
    @NoArgsConstructor
    public static class MemberGetNickDto { String nickName; }

    @Data
    @NoArgsConstructor
    public static class MemberPutExpDto {
        @NotBlank(message = "value for add is Empty or just Blank")
        String addingValue;
        @NotBlank(message = "Token value is Empty or just Blank")
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
