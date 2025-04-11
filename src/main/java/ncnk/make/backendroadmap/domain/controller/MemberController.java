package ncnk.make.backendroadmap.domain.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.controller.dto.member.MemberUpdateRequestDto;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.exception.SessionNullPointException;
import ncnk.make.backendroadmap.domain.security.auth.LoginUser;
import ncnk.make.backendroadmap.domain.security.auth.dto.SessionUser;
import ncnk.make.backendroadmap.domain.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

/**
 * 회원 업데이트 Controller
 */

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/mypage")
    public String myPage(@LoginUser SessionUser user, Model model,
        @SessionAttribute(name = "member", required = false) SessionUser sessionUser
    ) {
        loginValidate(user);

        model.addAttribute("userPicture", user.getPicture());
        Member member = memberService.findMemberByEmail(sessionUser.getEmail()); // 회원 검색
        model.addAttribute("memberID", member.getMemberId());
        model.addAttribute("Profile", member.getProfile());
        model.addAttribute("Email", member.getEmail());
        model.addAttribute("Name", member.getName());
        model.addAttribute("NickName", member.getNickName());
        model.addAttribute("Github", member.getGithub());
        model.addAttribute("Level", member.getLevel());
        model.addAttribute("Point", member.getPoint());
        model.addAttribute("hard", member.getHard());
        model.addAttribute("normal", member.getNormal());
        model.addAttribute("easy", member.getEasy());

        model.addAttribute("member", member); // 회원 정보를 model에 담고

        return "myPage/myPage";
    }

    @GetMapping("/edit/{memberId}")
    public String updateProfile(@PathVariable Long memberId, @LoginUser SessionUser user, Model model) {
        //로그인 하지 않은 사용자 접근 불가
        loginValidate(user);

        Member member = memberService.findMemberById(memberId); //회원 조회
        model.addAttribute("member", member); // 회원 정보를 model에 담고
        return "myPage/updateForm"; // updateForm.html로 반환
    }

    @PostMapping("/edit/{memberId}")
    public String update(@PathVariable Long memberId, @LoginUser SessionUser user, MultipartFile multipartFile,
        @ModelAttribute MemberUpdateRequestDto updateRequestDto) {
        //로그인 하지 않은 사용자 접근 불가
        loginValidate(user);

        Member member = memberService.findMemberById(memberId); //회원 조회
        memberService.updateProfile(member, multipartFile, updateRequestDto);// 회원 프로필 정보 업데이트 BIZ로직 실행

        return "redirect:/form/myPage/{memberId}";// myPage로 Redirect
    }

    private static void loginValidate(SessionUser user) {
        if (user == null) {
            throw new SessionNullPointException("[ERROR] SessionUser is null");
        }
    }
}

