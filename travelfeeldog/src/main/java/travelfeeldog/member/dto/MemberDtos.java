package travelfeeldog.member.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import travelfeeldog.global.auth.jwt.response.TokenResponse;
import travelfeeldog.member.domain.model.Member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDtos {

    @Getter
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
        private String passWord;

        public Member toEntity() {
            return new Member(nickName, email, passWord);
        }
    }

    @Getter
    @NoArgsConstructor
    @RequiredArgsConstructor
    public static class MemberLoginRequestDto {
        @NonNull
        private String email;
        @NonNull
        private String passWord;

        public Member toEntity() {
            return new Member(email, passWord);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class MemberPutNickNameDto {
        @NotBlank(message = "NickName value is Empty or just Blank")
        @Size(min = 2, max = 16, message = "닉네임은 2~16자리로 입력해주세요.")
        String nickName;
        @NotBlank(message = "Token value is Empty or just Blank")
        String firebaseToken;
    }

    @Getter
    @NotBlank(message = "Id value for found is Empty or just Blank")
    @NoArgsConstructor
    public static class MemberGetIdDto {
        String id;
    }

    @Getter
    @NotBlank(message = "NickName value is Empty or just Blank")
    @Size(min = 2, max = 16, message = "닉네임은 2~16자리로 입력해주세요.")
    @NoArgsConstructor
    public static class MemberGetNickDto {
        String nickName;
    }

    @Getter
    @NoArgsConstructor
    public static class MemberResponseExpDto {
        String level;
        String exp;

        public MemberResponseExpDto(Member member) {
            this.level = String.valueOf(member.getLevel());
            this.exp = String.valueOf(member.getExp());
        }
    }


    @Getter
    @NoArgsConstructor
    public static class MemberPutExpDto {
        @NotBlank(message = "value for add is Empty or just Blank")
        String addingValue;
        @NotBlank(message = "Token value is Empty or just Blank")
        String firebaseToken;
    }

    @Getter
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
    public static class MemberRegisterResponse {
        private final MemberPostResponseDto memberRegisterResponse;
        private final TokenResponse tokenResponse;

        public MemberRegisterResponse(MemberPostResponseDto memberPostResponseDto, TokenResponse tokenResponse) {
            this.memberRegisterResponse = memberPostResponseDto;
            this.tokenResponse = tokenResponse;
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

        public MemberPostResponseDto(Long id, String nickName, String imageUrl, String email, int level, int exp,
                                     String roleKey) {
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
