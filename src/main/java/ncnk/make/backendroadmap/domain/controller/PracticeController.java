package ncnk.make.backendroadmap.domain.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/practice")
public class PracticeController {

    @GetMapping()
    public String webCompiler(Model model, HttpServletRequest request) {
        //TODO 로그인 한 사용자를 조회해서 그 사람 id 값을 model에 담아서 반환해주기
        HttpSession session = request.getSession();

        Member member = (Member) session.getAttribute("member");

        model.addAttribute("userID", 1);
        return "ide";
    }
    

}
