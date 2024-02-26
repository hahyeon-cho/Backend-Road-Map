package ncnk.make.backendroadmap.domain.restController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.DocsLike;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.PracticeCode;
import ncnk.make.backendroadmap.domain.entity.Solved;
import ncnk.make.backendroadmap.domain.entity.SubCategory;
import ncnk.make.backendroadmap.domain.restController.dto.Member.MemberResponseDto;
import ncnk.make.backendroadmap.domain.restController.dto.Member.MyPracticeResponseDto;
import ncnk.make.backendroadmap.domain.restController.dto.Member.MyRoadMapResponseDto;
import ncnk.make.backendroadmap.domain.restController.dto.Member.MyTestResponseDto;
import ncnk.make.backendroadmap.domain.service.DocsLikeService;
import ncnk.make.backendroadmap.domain.service.MemberService;
import ncnk.make.backendroadmap.domain.service.PracticeCodeService;
import ncnk.make.backendroadmap.domain.service.SolvedService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//TODO: 마이페이지에서 Member 같은 속성값을 각 페이지마다 ResponseDto에 담아서 return 함! 리소스 낭비 예상!
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/member")
public class MemberApiController {
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

        Page<DocsLike> docsLikesPage = docsLikeService.findAllByMember(member, pageable);

        List<SubCategory> subCategories = docsLikesPage.getContent().stream()
                .map(DocsLike::getSubCategory)
                .collect(Collectors.toList());

        List<MyRoadMapResponseDto> myRoadMapResponseDto = new ArrayList<>();

        if (!subCategories.isEmpty()) {
            log.info("My RoadMap Set Data");
            for (SubCategory subCategory : subCategories) {
                MainCategory mainCategory = subCategory.getMainCategory();
                myRoadMapResponseDto.add(MyRoadMapResponseDto.createSubCategoryResponseDto(subCategory, mainCategory));
            }
            memberResponseDto.setRoadMapResponseDto(myRoadMapResponseDto);
        }

        return new MyPage(memberResponseDto.getProfile(), memberResponseDto.getEmail(),
                memberResponseDto.getName(), memberResponseDto.getNickName(),
                memberResponseDto.getGithub(), memberResponseDto.getLevel(),
                memberResponseDto.getPoint(), pageable.getPageSize(), memberResponseDto.getRoadMapResponseDto());
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
                myPracticeResponseDto.add(MyPracticeResponseDto.createMyPracticeResponseDto(practice));
            }
            memberResponseDto.setPracticeResponseDto(myPracticeResponseDto);
        }

        return new MyPage(memberResponseDto.getProfile(), memberResponseDto.getEmail(),
                memberResponseDto.getName(), memberResponseDto.getNickName(),
                memberResponseDto.getGithub(), memberResponseDto.getLevel(),
                memberResponseDto.getPoint(), pageable.getPageSize(), memberResponseDto.getPracticeResponseDto());
    }

//     동적쿼리로 찾기
//     예시 : http://localhost:8080/api/member/test/1?page=0&size=30&difficulty=Hard&order=desc&problemSolved=true
//     속성값 diffuculty - Hard - Middle - Easy order - asc - desc problemSolved - true - false

    @GetMapping("/test/{id}")
    public MyPage myTest(@PathVariable Long id,
                         @RequestParam(value = "difficulty", required = false) String difficulty,
                         @RequestParam(value = "order", required = false) String order,
                         @RequestParam(value = "problemSolved", required = false) Boolean problemSolved,
                         @PageableDefault(size = 5, direction = Direction.ASC) Pageable pageable) {
        Member member = memberService.findMemberById(id);
        MemberResponseDto memberResponseDto = MemberResponseDto.createMemberResponseDto(member);

        Page<Solved> solveds = solvedService.dynamicSearching(difficulty, order, problemSolved, pageable);

        List<MyTestResponseDto> myTestResponseDto = new ArrayList<>();
        if (!solveds.isEmpty()) {
            log.info("My Test Set Data");

            for (Solved solved : solveds) {
                myTestResponseDto.add(MyTestResponseDto.createTestResponseDto(solved, solved.getCodingTest()));
            }
            memberResponseDto.setTestResponseDto(myTestResponseDto);
        }

        return new MyPage(memberResponseDto.getProfile(), memberResponseDto.getEmail(),
                memberResponseDto.getName(), memberResponseDto.getNickName(),
                memberResponseDto.getGithub(), memberResponseDto.getLevel(),
                memberResponseDto.getPoint(), pageable.getPageSize(), memberResponseDto.getTestResponseDto());
    }

    @AllArgsConstructor
    @Getter
    static class MyPage<T> {
        private String profile;
        private String email;
        private String name;
        private String nickName;
        private String github;
        private int level;
        private int point;
        private int pageSize;
        private T data;
    }
}