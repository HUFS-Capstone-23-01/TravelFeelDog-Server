package travelfeeldog.domain.member.dto;
import lombok.*;
import travelfeeldog.domain.member.model.Member;


public class MemberDtos {

    @Data
    @NonNull //@NotNull과의 차이?? : @NotNull은 @Valid 통해서 검증, @NonNull은 null들어오면 NPE 발생
    @NoArgsConstructor
    public static class MemberPostRequestDto { //DB에 insert시 사용
        Long id;
        String nickName;
        int level;
        int exp;
        String imageUrl;
        String token;
    }

/* 필요시 작성 예정 Get/Put/
    public static class MemberRequestDto {

    }
    public static class MemberRequestDto {

    }
    public static class MemberRequestDto {

    }
    public static class MemberRequestDto {

    }
*/

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
            this(member.getId(), member.getNickName(), member.getLevel(), member.getExp(), member.getImageUrl(), member.getToken());
        }

        public MemberResponse(Long memberId, String nickName, int level, int exp, String imageUrl, String token) {
            this.id = memberId;
            this.nickName = nickName;
            this.level = level;
            this.exp = exp;
            this.imageUrl = imageUrl;
            this.token = token;
        }
    }
    //GET 값 하나씩

    //Response class


}