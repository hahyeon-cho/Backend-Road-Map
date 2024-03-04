package ncnk.make.backendroadmap.domain.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.security.auth.LoginUser;
import ncnk.make.backendroadmap.domain.security.auth.dto.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j

public class HomeController {


    @GetMapping("/")
    public String mainhome(@LoginUser SessionUser user, Model model){
        if (user != null) { //로그인한 사용자 접속 가능
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

        return "home";
    }
}
