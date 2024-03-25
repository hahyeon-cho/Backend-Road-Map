package ncnk.make.backendroadmap.domain.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.security.auth.LoginUser;
import ncnk.make.backendroadmap.domain.security.auth.dto.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 홈 Controller
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String mainhomepage(@LoginUser SessionUser user, Model model){
        //로그인한 사용자만 home.html에 접속가능
        if (user != null) {
            model.addAttribute("userName", user.getName());
            model.addAttribute("userPicture", user.getPicture());
        }
        return "mainHome";
    }

    @GetMapping("/home")
    public String homeLoginV3ArgumentResolver(@LoginUser SessionUser user, Model model) {
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        //TODO: dev/server 머지하고 mainPage.html로 return 값 변경하기!
        return "Login/login";
    }

    @GetMapping("/myPage")
    public String myPage(@LoginUser SessionUser user, Model model) {

       return "myPage/myPage";
    }
}
