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
import ncnk.make.backendroadmap.domain.restController.dto.Member.MemberRankingDto;
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

/**
 * 회원 RestController (json)
 */

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/member")
public class MemberApiController {
    private final MemberService memberService;
    private final DocsLikeService docsLikeService;
    private final PracticeCodeService practiceCodeService;
    private final SolvedService solvedService;

    // 마이페이지(MyRoadMap)
    //    http://localhost:8080/member/roadmap/1?page=2&size=5
    @GetMapping("/roadmap/{id}")
    public MyPage myRoad(@PathVariable Long id,
                         @PageableDefault(size = 5, direction = Direction.ASC) Pageable pageable) {
        Member member = memberService.findMemberById(id); //회원 PK값을 통해 회원 찾기 TODO: @Session 추가
        MemberResponseDto memberResponseDto = MemberResponseDto.createMemberResponseDto(member); //Return 할 dto 생성

        Page<DocsLike> docsLikesPage = docsLikeService.findAllByMember(member, pageable); //회원이 소분류에 누른 좋아요 찾기

        List<SubCategory> subCategories = docsLikesPage.getContent().stream()
                .map(DocsLike::getSubCategory)
                .collect(Collectors.toList()); //찾은 소분류 데이터를 List로 반환

        List<MyRoadMapResponseDto> myRoadMapResponseDto = new ArrayList<>();

        if (!subCategories.isEmpty()) {
            log.info("My RoadMap Set Data");
            for (SubCategory subCategory : subCategories) {
                MainCategory mainCategory = subCategory.getMainCategory();
                myRoadMapResponseDto.add(MyRoadMapResponseDto.createSubCategoryResponseDto(subCategory, mainCategory));
            }
            memberResponseDto.setRoadMapResponseDto(myRoadMapResponseDto); //MyRoadMap에 필요한 Fit한 데이터를 만들어서 dto로 Set
        }

        List<Member> findTop5Point = memberService.findTop5Point();
        List<MemberRankingDto> memberRankingDtos = new ArrayList<>();
        for (Member top5Member : findTop5Point) {
            memberRankingDtos.add(MemberRankingDto.createMemberRankingDto(top5Member));
        }
        memberResponseDto.setMemberRankingDtos(memberRankingDtos);

        return new MyPage(memberResponseDto.getProfile(), memberResponseDto.getEmail(),
                memberResponseDto.getName(), memberResponseDto.getNickName(),
                memberResponseDto.getGithub(), memberResponseDto.getLevel(),
                memberResponseDto.getPoint(), memberResponseDto.getHard(),
                memberResponseDto.getNormal(), memberResponseDto.getEasy(),
                memberResponseDto.getMemberRankingDtos(), pageable.getPageSize(),
                memberResponseDto.getRoadMapResponseDto());
    }

    // 마이페이지(MyPractice)
    @GetMapping("/practice/{id}")
    public MyPage myPractice(@PathVariable Long id,
                             @PageableDefault(size = 5, direction = Direction.ASC) Pageable pageable) {
        Member member = memberService.findMemberById(id); //회원 PK값을 통해 회원 찾기 TODO: @Session 추가
        MemberResponseDto memberResponseDto = MemberResponseDto.createMemberResponseDto(member); //Return 할 dto 생성

        Page<PracticeCode> practices = practiceCodeService.getPracticesByMember(member,
                pageable);  //회원이 저장한 웹 컴파일러 정보 찾기

        List<MyPracticeResponseDto> myPracticeResponseDto = new ArrayList<>();

        if (!practices.isEmpty()) {
            log.info("My Practice Set Data");

            for (PracticeCode practice : practices) {
                myPracticeResponseDto.add(MyPracticeResponseDto.createMyPracticeResponseDto(
                        practice)); //MyPractice에 필요한 Fit한 데이터를 만들어서 dto로 Set
            }
            memberResponseDto.setPracticeResponseDto(myPracticeResponseDto);
        }

        List<Member> findTop5Point = memberService.findTop5Point();
        List<MemberRankingDto> memberRankingDtos = new ArrayList<>();
        for (Member top5Member : findTop5Point) {
            memberRankingDtos.add(MemberRankingDto.createMemberRankingDto(top5Member));
        }
        memberResponseDto.setMemberRankingDtos(memberRankingDtos);

        return new MyPage(memberResponseDto.getProfile(), memberResponseDto.getEmail(),
                memberResponseDto.getName(), memberResponseDto.getNickName(),
                memberResponseDto.getGithub(), memberResponseDto.getLevel(),
                memberResponseDto.getPoint(), memberResponseDto.getHard(),
                memberResponseDto.getNormal(), memberResponseDto.getEasy(),
                memberResponseDto.getMemberRankingDtos(), pageable.getPageSize(),
                memberResponseDto.getPracticeResponseDto());
    }

    // 마이페이지(MyTest)
//     예시 : http://localhost:8080/api/member/test/1?page=0&size=30&difficulty=Hard&order=desc&problemSolved=true
//     속성값 diffuculty: Hard/Middle/Easy order: asc/desc problemSolved: true/false

    @GetMapping("/test/{id}")
    public MyPage myTest(@PathVariable Long id,
                         @RequestParam(value = "difficulty", required = false) String difficulty,
                         @RequestParam(value = "order", required = false) String order,
                         @RequestParam(value = "problemSolved", required = false) Boolean problemSolved,
                         @PageableDefault(size = 5, direction = Direction.ASC) Pageable pageable) {
        Member member = memberService.findMemberById(id); //회원 PK값을 통해 회원 찾기 TODO: @Session 추가
        MemberResponseDto memberResponseDto = MemberResponseDto.createMemberResponseDto(member); //Return 할 dto 생성

        //회원이 검색한 코딩테스트 찾기 (정렬: 난이도, 오름/내림차순, 풀이 여부)
        Page<Solved> solveds = solvedService.dynamicSearching(difficulty, order, problemSolved, pageable);

        List<MyTestResponseDto> myTestResponseDto = new ArrayList<>();
        if (!solveds.isEmpty()) {
            log.info("My Test Set Data");

            for (Solved solved : solveds) {
                myTestResponseDto.add(MyTestResponseDto.createTestResponseDto(solved,
                        solved.getCodingTest())); //MyTest에 필요한 Fit한 데이터를 만들어서 dto로 Set
            }
            memberResponseDto.setTestResponseDto(myTestResponseDto);
        }

        List<Member> findTop5Point = memberService.findTop5Point();
        List<MemberRankingDto> memberRankingDtos = new ArrayList<>();
        for (Member top5Member : findTop5Point) {
            memberRankingDtos.add(MemberRankingDto.createMemberRankingDto(top5Member));
        }
        memberResponseDto.setMemberRankingDtos(memberRankingDtos);

        return new MyPage(memberResponseDto.getProfile(), memberResponseDto.getEmail(),
                memberResponseDto.getName(), memberResponseDto.getNickName(),
                memberResponseDto.getGithub(), memberResponseDto.getLevel(),
                memberResponseDto.getPoint(), memberResponseDto.getHard(),
                memberResponseDto.getNormal(), memberResponseDto.getEasy(),
                memberResponseDto.getMemberRankingDtos(), pageable.getPageSize(),
                memberResponseDto.getTestResponseDto());
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
        private int hard;
        private int normal;
        private int easy;
        private T top5Ranking;
        private int pageSize;
        private T data;
    }
}