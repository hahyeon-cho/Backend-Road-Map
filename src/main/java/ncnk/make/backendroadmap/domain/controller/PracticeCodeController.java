package ncnk.make.backendroadmap.domain.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.exception.SessionNullPointException;
import ncnk.make.backendroadmap.domain.security.auth.LoginUser;
import ncnk.make.backendroadmap.domain.security.auth.dto.SessionUser;
import ncnk.make.backendroadmap.domain.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * 웹 컴파일러 Controller
 */
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/practice")
public class PracticeCodeController {
    private final MemberService memberService;

    @GetMapping()
    public String webCompiler(@LoginUser SessionUser user, Model model,
                              @SessionAttribute(name = "member", required = false) SessionUser sessionUser) {

        //로그인 하지 않은 사용자 접근 불가
        if (sessionUser == null) {
            throw new SessionNullPointException("[ERROR] SessionUser is null");
        }
        Member member = memberService.findMemberByEmail(sessionUser.getEmail()); //회원 찾기
        model.addAttribute("userID", member.getMemberId()); //회원 PK값 model에 담고
        model.addAttribute("userPicture", user.getPicture());

        return "navi/practice";
    }
    @GetMapping("/ide")
    public String wevCompilerIde() {
    //ide.html로 반환
        return "ide";
    }


}
