package travelfeeldog.web.presentaion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import travelfeeldog.global.secure.auth.LoginUser;
import travelfeeldog.global.secure.auth.dto.SessionUser;

@RequiredArgsConstructor
@Controller
public class IndexController {
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        if (user != null) {
            model.addAttribute("userName", user.getNickName());
            model.addAttribute("userRole", user.getRole());  // role 정보 추가
        }
        return "index";
    }
}
