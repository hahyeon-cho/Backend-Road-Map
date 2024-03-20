package ncnk.make.backendroadmap.domain.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.controller.dto.Member.MemberUpdateRequestDto;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberUpdateController {
    private final MemberService memberService;

    @GetMapping("/edit/{memberId}")
    public String updateProfile(@PathVariable Long memberId, Model model) {
        Member member = memberService.findMemberById(memberId);
        model.addAttribute("member", member);
        return "myPage/updateForm";
    }

    @PostMapping("/edit/{memberId}")
    public String update(@PathVariable Long memberId,
                         @ModelAttribute MemberUpdateRequestDto updateRequestDto) {
        Member member = memberService.findMemberById(memberId);
        memberService.updateProfile(member, updateRequestDto);

        return "redirect:/form/myPage/{memberId}";
    }
}

