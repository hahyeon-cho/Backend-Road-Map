package com.make.backendroadmap.domain.controller;

import com.make.backendroadmap.domain.controller.dto.Member.MemberResponseDto;
import com.make.backendroadmap.domain.controller.dto.Member.MyPracticeResponseDto;
import com.make.backendroadmap.domain.controller.dto.Member.MyRoadMapResponseDto;
import com.make.backendroadmap.domain.controller.dto.Member.MyTestResponseDto;
import com.make.backendroadmap.domain.entity.DocsLike;
import com.make.backendroadmap.domain.entity.Member;
import com.make.backendroadmap.domain.entity.PracticeCode;
import com.make.backendroadmap.domain.entity.Solved;
import com.make.backendroadmap.domain.entity.SubCategory;
import com.make.backendroadmap.domain.repository.DocsLikeRepository;
import com.make.backendroadmap.domain.repository.MemberRepository;
import com.make.backendroadmap.domain.repository.SubCategory.SubCategoryRepository;
import com.make.backendroadmap.domain.service.DocsLikeService;
import com.make.backendroadmap.domain.service.MemberService;
import com.make.backendroadmap.domain.service.PracticeCodeService;
import com.make.backendroadmap.domain.service.SolvedService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO: 마이페이지에서 Member 같은 속성값을 각 페이지마다 ResponseDto에 담아서 return 함! 리소스 낭비 예상!
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {
    private final DocsLikeRepository docsLikeRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final DocsLikeService docsLikeService;
    private final PracticeCodeService practiceCodeService;

    private final SolvedService solvedService;

    //    http://localhost:8080/member/roadmap/1?page=2&size=5
    @GetMapping("/roadmap/{id}")
    public MyPage myRoad(@PathVariable Long id,
                         @PageableDefault(size = 5, direction = Direction.ASC) Pageable pageable) {
        Member member = memberService.findMemberById(id);
        MemberResponseDto memberResponseDto = MemberResponseDto.createMemberResponseDto(member);

        Page<DocsLike> docsLikesPage = docsLikeRepository.findAllByMember(member, pageable);

        List<SubCategory> subCategories = docsLikesPage.getContent().stream()
                .map(DocsLike::getSubCategory)
                .collect(Collectors.toList());

        List<MyRoadMapResponseDto> myRoadMapResponseDto = new ArrayList<>();

        if (!subCategories.isEmpty()) {
            log.info("My RoadMap Set Data");
            for (SubCategory subCategory : subCategories) {
                myRoadMapResponseDto.add(MyRoadMapResponseDto.createSubCategoryResponseDto(
                        subCategory.getSubDocsTitle(), subCategory.getSubDocsUrl()));
            }
            memberResponseDto.setRoadMapResponseDto(myRoadMapResponseDto);
        }

        return new MyPage(memberResponseDto.getProfile(), memberResponseDto.getEmail(),
                memberResponseDto.getName(), memberResponseDto.getGithub(), memberResponseDto.getLevel(),
                pageable.getPageSize(), memberResponseDto.getRoadMapResponseDto());
    }

    @GetMapping("/practice/{id}")
    public MyPage myPractice(@PathVariable Long id,
                             @PageableDefault(size = 5, direction = Direction.ASC) Pageable pageable) {
        Member member = memberService.findMemberById(id);
        MemberResponseDto memberResponseDto = MemberResponseDto.createMemberResponseDto(member);

        Page<PracticeCode> practices = practiceCodeService.getPracticesByMember(member, pageable);

        for (PracticeCode practice : practices) {
            log.info("Practice ={}", practice.getMember());
        }

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
                pageable.getPageSize(), memberResponseDto.getPracticeResponseDto());
    }

    @GetMapping("/test/{id}")
    public MyPage myTest(@PathVariable Long id,
                         @PageableDefault(size = 5, direction = Direction.ASC) Pageable pageable) {
        Member member = memberService.findMemberById(id);
        MemberResponseDto memberResponseDto = MemberResponseDto.createMemberResponseDto(member);

        Page<Solved> solveds = solvedService.getSolved(member, pageable);

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
                pageable.getPageSize(), memberResponseDto.getTestResponseDto());
    }

    @AllArgsConstructor
    @Getter
    static class MyPage<T> {
        private String profile;
        private String email;
        private String name;
        private String github;
        private int level;
        private int pageSize;
        private T data;
    }
}
