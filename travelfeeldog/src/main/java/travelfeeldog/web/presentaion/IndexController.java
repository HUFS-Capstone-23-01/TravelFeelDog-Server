package travelfeeldog.web.presentaion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import travelfeeldog.domain.community.feed.service.FeedReadService;
import travelfeeldog.global.secure.auth.LoginUser;
import travelfeeldog.global.secure.auth.dto.SessionUser;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final FeedReadService feedReadService;

    @GetMapping("/home")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", feedReadService.getListAll(1));
        if (user != null) {
            model.addAttribute("userName", user.getNickName());
        }
        return "index";
    }
    @GetMapping("/login/oauth2/code/google")
    public String sho(){
        return "hello world";
    }
}
