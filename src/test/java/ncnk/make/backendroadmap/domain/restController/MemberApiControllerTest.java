package ncnk.make.backendroadmap.domain.restController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.CodingTest;
import ncnk.make.backendroadmap.domain.entity.DocsLike;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.PracticeCode;
import ncnk.make.backendroadmap.domain.entity.Role;
import ncnk.make.backendroadmap.domain.entity.Solved;
import ncnk.make.backendroadmap.domain.entity.Sub;
import ncnk.make.backendroadmap.domain.entity.SubCategory;
import ncnk.make.backendroadmap.domain.restController.dto.Member.MemberResponseDto;
import ncnk.make.backendroadmap.domain.service.CodingTestService;
import ncnk.make.backendroadmap.domain.service.DocsLikeService;
import ncnk.make.backendroadmap.domain.service.MainCategoryService;
import ncnk.make.backendroadmap.domain.service.MemberService;
import ncnk.make.backendroadmap.domain.service.PracticeCodeService;
import ncnk.make.backendroadmap.domain.service.SolvedService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Slf4j
@Transactional
class MemberApiControllerTest {
    @Autowired
    private MainCategoryService mainCategoryService;

    @PersistenceContext
    EntityManager em;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @Autowired
    private DocsLikeService docsLikeService;

    @Autowired
    private PracticeCodeService practiceCodeService;

    @Autowired
    private CodingTestService codingTestService;

    @Autowired
    private SolvedService solvedService;

    private MockHttpSession session;

    private Member member;

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
        member = createMember();
        session.setAttribute("member", member);
    }

    @DisplayName("마이페이지: myRoadMap")
    @ParameterizedTest(name = "{index} {displayName} arguments = {arguments}")
    @EnumSource(value = Sub.class, names = {"IP", "TCP_UDP", "PORT", "DNS", "HTTP"})
    public void myRoadTest(Sub sub) throws Exception {
        // given
        Member member = memberService.findMemberById(1L);
        MemberResponseDto memberResponseDto = MemberResponseDto.createMemberResponseDto(member);
        MainCategory mainCategory = mainCategoryService.findMainCategoryById(1L);
        SubCategory subCategory = SubCategory.createSubCategory(sub, 0L, sub.getSubDescription(), mainCategory);

        DocsLike docsLike = DocsLike.createDocsLike(subCategory, member);
        List<DocsLike> docsLikes = new ArrayList<>();
        docsLikes.add(docsLike);
        Page<DocsLike> docsLikePage = new PageImpl<>(docsLikes);
        List<DocsLike> collect = docsLikePage.get().collect(Collectors.toList());

        // when & then
        mockMvc.perform(get("/api/member/roadmap/{id}", member.getMemberId()))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(memberResponseDto.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(memberResponseDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.level").value(memberResponseDto.getLevel()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[%d].subDocsTitle", sub.ordinal())
                        .value(collect.get(0).getSubCategory().getSubDocsTitle().getSubCategory()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[%d].subDescription", sub.ordinal())
                        .value(collect.get(0).getSubCategory().getSubDocsTitle().getSubDescription()))
                .andExpect(status().isOk());
    }

    @DisplayName("마이페이지: myPractice")
    @Test
    public void myPracticeTest() throws Exception {
        Member member = memberService.findMemberById(1L);
        MemberResponseDto memberResponseDto = MemberResponseDto.createMemberResponseDto(member);
        PracticeCode practiceCode = createPracticeCode(member);

        List<PracticeCode> practiceCodes = new ArrayList<>();
        practiceCodes.add(practiceCode);
        Page<PracticeCode> practiceCodePage = new PageImpl<>(practiceCodes);
        List<PracticeCode> collect = practiceCodePage.get().collect(Collectors.toList());

        mockMvc.perform(get("/api/member/practice/{id}", member.getMemberId()))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(memberResponseDto.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(memberResponseDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.level").value(memberResponseDto.getLevel()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].fileName")
                        .value(collect.get(0).getFileName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].language")
                        .value(collect.get(0).getLanguage()))
                .andExpect(status().isOk());
    }

    @DisplayName("마이페이지: myTest")
    @Test
    public void myTestTest() throws Exception {
        Member member = memberService.findMemberById(1L);
        MemberResponseDto memberResponseDto = MemberResponseDto.createMemberResponseDto(member);
        CodingTest codingTest = createCodingTest();

        Solved solved = Solved.createSolved(codingTest, member, false, "problemPath");
        List<Solved> solveds = new ArrayList<>();
        solveds.add(solved);

        Page<Solved> solvedPage = new PageImpl<>(solveds);
        List<Solved> collect = solvedPage.get().collect(Collectors.toList());

        mockMvc.perform(get("/api/member/test/{id}", member.getMemberId())
                        .param("difficulty", "Hard")
                        .param("order", "desc")
                        .param("problemSolved", "true"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(memberResponseDto.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(memberResponseDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.level").value(memberResponseDto.getLevel()))
                .andExpect(status().isOk());
    }

    private Member createMember() {
        Member member = Member.createMember("profile", "email1", "name", "nickname", "github", 1, 0, Role.GUEST);
        em.persist(member);
        return member;
    }

    private PracticeCode createPracticeCode(Member member) {
        PracticeCode practiceCode = PracticeCode.createPracticeCode("fileName", "filePath", "java", member);
        em.persist(practiceCode);
        return practiceCode;
    }

    private CodingTest createCodingTest() {
        CodingTest codingTest = CodingTest.createCodingTest("problemTitle", "problemSlug", "problemLevel", 10.0,
                "problemContents", Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        em.persist(codingTest);
        return codingTest;
    }
}