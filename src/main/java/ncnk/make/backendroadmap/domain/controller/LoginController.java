package ncnk.make.backendroadmap.domain.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.security.auth.LoginUser;
import ncnk.make.backendroadmap.domain.security.auth.dto.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    // 이 매핑은 프로필을 수정하기 위한 페이지를 불러옵니다. 사용자가 프로필을 수정하고자 할 때 먼저 해당 페이지로 이동해야 합니다.
    // 이 매핑은 GET 요청을 처리하여 프로필 수정 페이지를 보여줍니다.
    @GetMapping("/login")
    public String login(@LoginUser SessionUser user, Model model) {
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        //TODO: dev/server 머지하고 mainPage.html로 return 값 변경하기!

        return "Login/login";
    }


    @PostMapping("/logout")
    public String Logout(HttpServletRequest request) {
        // 세션삭제 부분
//        request.removeAttribute("member");
        HttpSession session = request.getSession(false);
        // session이 null이 아니라는건 기존에 세션이 존재했었다는 뜻이므로
        // 세션이 null이 아니라면 session.invalidate()로 세션 삭제해주기.
        if (session != null) {
            session.invalidate();
        }
        return "/mainHome";
    }
}

