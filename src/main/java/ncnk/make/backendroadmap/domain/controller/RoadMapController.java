package ncnk.make.backendroadmap.domain.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.exception.SessionNullPointException;
import ncnk.make.backendroadmap.domain.security.auth.LoginUser;
import ncnk.make.backendroadmap.domain.security.auth.dto.SessionUser;
import ncnk.make.backendroadmap.domain.service.MemberService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * 로드맵 Controller
 */
@Controller
@RequiredArgsConstructor
@Slf4j

@RequestMapping("/roadmap")
public class RoadMapController {

    private final MemberService memberService;

    @GetMapping()
    public String roadMapMain(@LoginUser SessionUser user, Model model,
                              @SessionAttribute(name = "member", required = false) SessionUser sessionUser){
        if(sessionUser == null){
            throw new SessionNullPointException("[ERROR] SessionUser is null");
        }
        Member member = memberService.findMemberByEmail(sessionUser.getEmail()); // 회원 검색
        model.addAttribute("userID", member.getMemberId());
        model.addAttribute("userPicture", user.getPicture());
        model.addAttribute("Level", member.getLevel());
        return "roadMap/roadMapMain";
    }

    @GetMapping("/subcategory")
    public String roadMapSub() {
        return "roadMap/roadMapSub";
    }
}