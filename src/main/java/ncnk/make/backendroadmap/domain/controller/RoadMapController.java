package ncnk.make.backendroadmap.domain.controller;


import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.controller.dto.docsLike.DocsLikeResponseDto;
import ncnk.make.backendroadmap.domain.entity.DocsLike;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.exception.SessionNullPointException;
import ncnk.make.backendroadmap.domain.security.auth.LoginUser;
import ncnk.make.backendroadmap.domain.security.auth.dto.SessionUser;
import ncnk.make.backendroadmap.domain.service.DocsLikeService;
import ncnk.make.backendroadmap.domain.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 로드맵 Controller
 */
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/roadmap")
public class RoadMapController {

    private final MemberService memberService;
    private final DocsLikeService docsLikeService;

    @GetMapping()
    public String roadMapMain(@LoginUser SessionUser user, Model model) {
        loginValidate(user);
        Member member = memberService.findMemberByEmail(user.getEmail()); // 회원 검색
        model.addAttribute("userID", member.getMemberId());
        model.addAttribute("userPicture", user.getPicture());
        model.addAttribute("Level", member.getLevel());
        return "roadMap/roadMapMain";
    }

    private static void loginValidate(SessionUser user) {
        if (user == null) {
            throw new SessionNullPointException("[ERROR] SessionUser is null");
        }
    }

    @GetMapping("/subcategory")
    public String roadMapSub(@LoginUser SessionUser user, Model model) {
        loginValidate(user);
        Member member = memberService.findMemberByEmail(user.getEmail()); // 회원 검색

        List<DocsLike> docsLikesByMember = docsLikeService.findDocsLikesByMember(member);
        List<DocsLikeResponseDto> docsLikeResponseDtos = new ArrayList<>();

        for (DocsLike docsLike : docsLikesByMember) {
            docsLikeResponseDtos.add(DocsLikeResponseDto.createDocsLikeResponseDto(docsLike.getLikeId()));
        }
        model.addAttribute("docsLikeResponseDtos", docsLikeResponseDtos);
        model.addAttribute("userID", member.getMemberId());
        model.addAttribute("userPicture", member.getProfile());
        model.addAttribute("Level", member.getLevel());
        return "roadMap/roadMapSub";
    }
}