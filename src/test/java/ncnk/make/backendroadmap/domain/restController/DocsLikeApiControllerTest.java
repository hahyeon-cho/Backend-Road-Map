package ncnk.make.backendroadmap.domain.restController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.Role;
import ncnk.make.backendroadmap.domain.entity.SubCategory;
import ncnk.make.backendroadmap.domain.service.SubCategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc // MockMvc 자동 구성
@ActiveProfiles("test")
@Slf4j
@Transactional
class DocsLikeApiControllerTest {

    @PersistenceContext
    EntityManager em;

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;

    @Autowired
    private SubCategoryService subCategoryService;

    private MockHttpSession session;
    private Member member;

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
        member = createMember();
        session.setAttribute("member", member);
    }

    @AfterEach
    public void clean() {
        session.clearAttributes();
    }


    @DisplayName("좋아요 토글 - 로그인한 사용자")
    @ParameterizedTest(name = "{index} {displayName} arguments = {arguments}")
    @ValueSource(longs = {1, 2, 3, 4, 5})
//    @CsvSource({"INTERNET, IP", "BASIC_FE, HTML", "OS, OS_WORK", "LANGUAGE, JAVA", "ALGORITHM, BUBBLE_SORT"})
    void toggleDocsLike_WithLoggedInUser(long id) throws Exception {
        SubCategory subCategory = subCategoryService.findSubCategoryById(id);

        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        mockMvc.perform(post("/api/like/{id}", subCategory.getSubDocsId())
                .session(session))
            .andExpect(status().isOk());
    }

    @DisplayName("좋아요 토글 - 비로그인 사용자")
    @ParameterizedTest(name = "{index} {displayName} arguments = {arguments}")
    @ValueSource(longs = {1, 2, 3, 4, 5})
    void toggleDocsLike_WithoutLoggedInUser(long id) throws Exception {
        SubCategory subCategory = subCategoryService.findSubCategoryById(id);

        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        mockMvc.perform(post("/api/like/{id}", subCategory.getSubDocsId()))
            .andExpect(status().is4xxClientError());
    }

    private Member createMember() {
        Member member = Member.createMember("profile", "email1", "name", "nickname", "github",
            1, 0, Role.GUEST, 0, 0, 0);
        em.persist(member);
        return member;
    }
}