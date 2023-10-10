package travelfeeldog.member.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import travelfeeldog.member.domain.model.Member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import travelfeeldog.member.domain.model.Role;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDtos {

    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor
    public static class MemberPostRequestDto {
        @NonNull
        @NotBlank(message = "NickName value is Empty or just Blank")
        @Size(min = 2, max = 16, message = "닉네임은 2~16자리로 입력해주세요.")
        private String nickName;
        @NonNull
        private String email;
        @NonNull
        @NotBlank(message = "Token value is Empty or just Blank")
        private String firebaseToken;
    }

    @Data
    @NoArgsConstructor
    public static class MemberPutNickNameDto {
        @NotBlank(message = "NickName value is Empty or just Blank")
        @Size(min = 2, max = 16, message = "닉네임은 2~16자리로 입력해주세요.")
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
    @Size(min = 2, max = 16, message = "닉네임은 2~16자리로 입력해주세요.")
    @NoArgsConstructor
    public static class MemberGetNickDto { String nickName; }

    @Data
    @NoArgsConstructor
    public static class MemberResponseExpDto {
        String level;
        String exp;

        public MemberResponseExpDto(Member member) {
            this.level = String.valueOf(member.getLevel());
            this.exp = String.valueOf(member.getExp());
        }
    }


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
        // fix point
        public MemberResponse(Member member) {
            this(
                    member.getId(),
                    member.getNickName(),
                    member.getLevel(),
                    member.getExp(),
                    member.getImageUrl(),
                    member.getRoleKey() // fix point
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
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MemberPostResponseDto {
        private Long id;
        private String nickName;
        private String email;
        private int level;
        private int exp;
        private String imageUrl;
        private String role;

        public MemberPostResponseDto(Member member) {
            this(
                member.getId(),
                member.getNickName(),
                member.getImageUrl(),
                member.getEmail(),
                member.getLevel(),
                member.getExp(),
                member.getRoleKey()  // fix point
            );
        }
        public MemberPostResponseDto(Long id, String nickName,String imageUrl,String email, int level, int exp,String  roleKey) {
            this.id = id;
            this.email = email;
            this.nickName = nickName;
            this.level = level;
            this.exp = exp;
            this.imageUrl = imageUrl;
            this.role = roleKey;
        }
    }
}
