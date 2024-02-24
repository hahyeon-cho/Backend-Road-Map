package ncnk.make.backendroadmap.domain.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.exception.SessionNullPointException;
import ncnk.make.backendroadmap.domain.security.auth.dto.SessionUser;
import ncnk.make.backendroadmap.domain.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/practice")
public class PracticeCodeController {
    private final MemberService memberService;

    @GetMapping()
    public String webCompiler(Model model,
                              @SessionAttribute(name = "member", required = false) SessionUser sessionUser) {

        if (sessionUser == null) {
            throw new SessionNullPointException("[ERROR] SessionUser is null");
        }

        Member member = memberService.findMemberByEmail(sessionUser.getEmail());
        model.addAttribute("userID", member.getMemberId());

        return "ide";
    }


}
