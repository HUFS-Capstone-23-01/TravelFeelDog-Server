package travelfeeldog.global.secure.auth.dto;

import java.io.Serializable;
import lombok.Getter;
import travelfeeldog.member.domain.model.Member;

@Getter
public class SessionUser implements Serializable {
    private String nickName;
    private String email;
    private String role;
    public SessionUser(Member user) {
        this.nickName = user.getNickName();
        this.email = user.getEmail();
        this.role = user.getRoleKey();
    }
}