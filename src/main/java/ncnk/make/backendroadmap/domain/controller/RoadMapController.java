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
@RequestMapping("/roadmap")
public class RoadMapController {
    private final MemberService memberService;
    @GetMapping()
    public String roadMapMain(Model model,
                              @SessionAttribute(name = "member", required = false) SessionUser sessionUser) {
        if (sessionUser == null) { //로그인 하지 않은 사용자 점속 불가
        throw new SessionNullPointException("[ERROR] SessionUser is null");
    }
        Member member = memberService.findMemberByEmail(sessionUser.getEmail()); // 회원 검색
        model.addAttribute("userID", member.getMemberId());

        return "roadMap/roadMapMain";
    }

    @GetMapping("/internet")
    public String roadMapsub() {
        return "roadMap/roadMapsub";
    }

    @GetMapping("/internet/detail")
    public String roadMapdetail() {
        return "roadMap/roadMapdetail";
    }

}