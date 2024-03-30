package ncnk.make.backendroadmap.domain.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.controller.dto.Member.MemberUpdateRequestDto;
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

/**
 * 회원 업데이트 Controller
 */
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/myPage/{id}")
    public String myPage(@PathVariable Long memberId, @LoginUser SessionUser user, Model model) {
        //로그인 하지 않은 사용자 접근 불가
        loginValidate(user);

        Member member = memberService.findMemberById(memberId); //회원 조회
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
    public String update(@PathVariable Long memberId, @LoginUser SessionUser user,
                         @ModelAttribute MemberUpdateRequestDto updateRequestDto) {
        //로그인 하지 않은 사용자 접근 불가
        loginValidate(user);

        Member member = memberService.findMemberById(memberId); //회원 조회
        memberService.updateProfile(member, updateRequestDto);// 회원 프로필 정보 업데이트 BIZ로직 실행

        return "redirect:/form/myPage/{memberId}";// myPage로 Redirect
    }

    private static void loginValidate(SessionUser user) {
        if (user == null) {
            throw new SessionNullPointException("[ERROR] SessionUser is null");
        }
    }
}

