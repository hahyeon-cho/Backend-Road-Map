package com.make.backendroadmap.domain.controller;

import com.make.backendroadmap.domain.controller.dto.Member.MemberResponseDto;
import com.make.backendroadmap.domain.controller.dto.Member.MemberUpdateRequestDto;
import com.make.backendroadmap.domain.controller.dto.Member.MyPracticeResponseDto;
import com.make.backendroadmap.domain.controller.dto.Member.MyRoadMapResponseDto;
import com.make.backendroadmap.domain.controller.dto.Member.MyTestResponseDto;
import com.make.backendroadmap.domain.entity.Member;
import com.make.backendroadmap.domain.entity.PracticeCode;
import com.make.backendroadmap.domain.entity.Solved;
import com.make.backendroadmap.domain.entity.SubCategory;
import com.make.backendroadmap.domain.service.DocsLikeService;
import com.make.backendroadmap.domain.service.MemberService;
import com.make.backendroadmap.domain.service.PracticeCodeService;
import com.make.backendroadmap.domain.service.SolvedService;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final DocsLikeService docsLikeService;
    private final PracticeCodeService practiceCodeService;

    private final SolvedService solvedService;

    @GetMapping("/roadmap/{id}")
    public MyPage myRoad(@PathVariable Long id) {
        Member member = memberService.findMemberById(id);
        MemberResponseDto memberResponseDto = MemberResponseDto.createMemberResponseDto(member);

        List<SubCategory> subCategoriesLike = docsLikeService.findSubCategoriesByMember(member);

        List<MyRoadMapResponseDto> myRoadMapResponseDto = new ArrayList<>();

        if (!subCategoriesLike.isEmpty()) {
            log.info("My RoadMap Set Data");

            for (SubCategory subCategory : subCategoriesLike) {
                myRoadMapResponseDto.add(MyRoadMapResponseDto.createSubCategoryResponseDto(
                        subCategory.getSubDocsTitle(), subCategory.getSubDocsUrl()));
            }
            memberResponseDto.setRoadMapResponseDto(myRoadMapResponseDto);
        }

        return new MyPage(memberResponseDto.getProfile(), memberResponseDto.getEmail(),
                memberResponseDto.getName(), memberResponseDto.getGithub(), memberResponseDto.getLevel(),
                memberResponseDto.getRoadMapResponseDto());
    }

    @GetMapping("/practice/{id}")
    public MyPage myPractice(@PathVariable Long id) {
        Member member = memberService.findMemberById(id);
        MemberResponseDto memberResponseDto = MemberResponseDto.createMemberResponseDto(member);

        List<PracticeCode> practices = practiceCodeService.getPracticesByMember(member);

        List<MyPracticeResponseDto> myPracticeResponseDto = new ArrayList<>();

        if (!practices.isEmpty()) {
            log.info("My Practice Set Data");

            for (PracticeCode practice : practices) {
                myPracticeResponseDto.add(MyPracticeResponseDto.createMyPracticeResponseDto(
                        practice.getTitle(), practice.getLanguage()));
            }
            memberResponseDto.setPracticeResponseDto(myPracticeResponseDto);
        }

        return new MyPage(memberResponseDto.getProfile(), memberResponseDto.getEmail(),
                memberResponseDto.getName(), memberResponseDto.getGithub(), memberResponseDto.getLevel(),
                memberResponseDto.getPracticeResponseDto());
    }

    @GetMapping("/test/{id}")
    public MyPage myTest(@PathVariable Long id) {
        Member member = memberService.findMemberById(id);
        MemberResponseDto memberResponseDto = MemberResponseDto.createMemberResponseDto(member);

        List<Solved> solveds = solvedService.getSolved(member);

        List<MyTestResponseDto> myTestResponseDto = new ArrayList<>();

        if (!solveds.isEmpty()) {
            log.info("My Test Set Data");

            for (Solved solved : solveds) {
                myTestResponseDto.add(MyTestResponseDto.createTestResponseDto(solved, solved.getCodingTest()));
            }

            memberResponseDto.setTestResponseDto(myTestResponseDto);
        }

        return new MyPage(memberResponseDto.getProfile(), memberResponseDto.getEmail(),
                memberResponseDto.getName(), memberResponseDto.getGithub(), memberResponseDto.getLevel(),
                memberResponseDto.getTestResponseDto());
    }


    @GetMapping("/edit/{memberId}")
    public String updateProfile(@PathVariable Long memberId, Model model) {
        Member member = memberService.findMemberById(memberId);
        model.addAttribute("member", member);
        return "form/updateForm";
    }

    @PostMapping("/edit/{memberId}")
    public String update(@PathVariable Long memberId,
                         @ModelAttribute MemberUpdateRequestDto updateRequestDto) {
        Member member = memberService.findMemberById(memberId);
        memberService.updateProfile(member, updateRequestDto);

        return "redirect:/form/myPage/{memberId}";
    }

    @AllArgsConstructor
    static class MyPage<T> {
        private String profile;
        private String email;
        private String name;
        private String github;
        private int level;
        private T data;
    }
}
