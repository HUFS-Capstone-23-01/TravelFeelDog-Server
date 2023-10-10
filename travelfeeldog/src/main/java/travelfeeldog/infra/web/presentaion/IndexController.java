package travelfeeldog.infra.web.presentaion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import travelfeeldog.global.auth.LoginUser;
import travelfeeldog.member.domain.model.Member;


@RequiredArgsConstructor
@Controller
public class IndexController {
    @GetMapping("/")
    public String index(Model model, @LoginUser Member member) {
        if (member != null) {
            model.addAttribute("userName", member.getNickName());
            model.addAttribute("userRole", member.getRole());  // role 정보 추가
        }else{
            throw new RuntimeException();
        }
        return "index";
    }
}
