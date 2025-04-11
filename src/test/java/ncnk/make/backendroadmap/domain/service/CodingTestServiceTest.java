package ncnk.make.backendroadmap.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.api.leetcode.LeetCodeApi;
import ncnk.make.backendroadmap.domain.aop.time.callback.TraceTemplate;
import ncnk.make.backendroadmap.domain.entity.CodingTest;
import ncnk.make.backendroadmap.domain.entity.Problem;
import ncnk.make.backendroadmap.domain.repository.codingTest.CodingTestRepository;
import ncnk.make.backendroadmap.domain.utils.LeetCode.LeetCodeCrawling;
import ncnk.make.backendroadmap.domain.utils.LeetCode.WebDriverPool;
import ncnk.make.backendroadmap.domain.utils.LeetCode.wrapper.CodingTestAnswer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@Slf4j
class CodingTestServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private TraceTemplate template;

    @InjectMocks
    private CodingTestService codingTestService;

    @Mock
    private CodingTestRepository codingTestRepository;

    @Mock
    private CodingTest codingTest;

    @Mock
    private LeetCodeApi leetCodeApi;
    @Mock
    private LeetCodeCrawling leetcodeCrawling;
    @Mock
    private WebDriverPool webDriverPool;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        codingTestService = new CodingTestService(codingTestRepository, leetCodeApi, leetcodeCrawling, webDriverPool,
            template);
    }

    @DisplayName("예상 입출력 중 첫 번째 예상 입력에 대한 예상 출력 값과 사용자가 웹 컴파일러를 통해 결과를 반환한 것을 비교한다.")
    @ParameterizedTest(name = "{index} {displayName} arguments = {arguments}")
    @ValueSource(strings = {"output"})
    void evaluateCodingTest(String userCodeResult) {
        //given
        String json = "[{\"input\":\"input\", \"output\":\"output\"}]";
        JSONArray jsonArray = new JSONArray(json);

        List<CodingTestAnswer> codingTestAnswers = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String input = jsonObject.getString("input");
            String output = jsonObject.getString("output");
            CodingTestAnswer codingTestAnswer = CodingTestAnswer.createCodingTestAnswer(input, output);
            codingTestAnswers.add(codingTestAnswer);
        }

        // when
        boolean result = codingTestService.evaluateCodingTest(userCodeResult, codingTestAnswers);

        // then
        assertTrue(result);
    }

    @DisplayName("알고리즘 문제는 사용자가 풀지 않은 문제 중 하/하/중 3문제를 랜덤으로 뽑는다.")
    @Test
    void findRandomProblemsByLevel_ReturnsCorrectlyCombinedList() {
        // given
        List<CodingTest> normalProblems = new ArrayList<>();
        normalProblems.add(createNormalLevel());

        List<CodingTest> easyProblems = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            easyProblems.add(createEasyLevel());
        }

        when(codingTestRepository.findRandomProblemsByLevel(Problem.NORMAL.getProblemLevel(), 1))
            .thenReturn(normalProblems);
        when(codingTestRepository.findRandomProblemsByLevel(Problem.EASY.getProblemLevel(), 2))
            .thenReturn(easyProblems);

        // when
        List<CodingTest> result = codingTestService.findRandomProblemsByLevel();

        // then
        assertAll(() -> assertEquals(3, result.size()),
            () -> assertTrue(result.stream()
                .anyMatch(problem -> problem.getProblemLevel().equals(Problem.NORMAL.getProblemLevel()))),
            () -> assertTrue(result.stream()
                .filter(problem -> problem.getProblemLevel().equals(Problem.EASY.getProblemLevel()))
                .count() == 2),
            () -> verify(codingTestRepository).findRandomProblemsByLevel(Problem.NORMAL.getProblemLevel(), 1),
            () -> verify(codingTestRepository).findRandomProblemsByLevel(Problem.EASY.getProblemLevel(), 2));
    }

    @DisplayName("코딩 테스트 PK 값으로 알고리즘 문제 찾기")
    @ParameterizedTest(name = "{index} {displayName} arguments = {arguments}")
    @CsvSource({"31, contents0", "32, contents1", "33, contents2", "34, contents3", "35, contents4"})
    void findCodingTestByIdTest(Long id, String problemContents) {
        // given
        CodingTest codingTest = createCodingTest(problemContents);

        when(codingTestRepository.findCodingTestByCodingTestId(id)).thenReturn(Optional.of(codingTest));

        // when
        CodingTest findCodingTest = codingTestService.findCodingTestById(id);

        // then
        assertAll(() -> assertThat(findCodingTest.getCodingTestId()).isEqualTo(id),
            () -> assertThat(findCodingTest.getProblemContents()).isEqualTo(problemContents));
    }

    @DisplayName("문제 리스트에서 정렬 기능")
    @ParameterizedTest(name = "{index} {displayName} arguments = {arguments}")
    @CsvSource({"Hard, asc, solved", "Normal, desc, solved", "Easy, asc, unsolved"})
    void dynamicSearchingTest(String problemLevel, String problemAccuracy, String status) {
        // given
        Pageable pageable = PageRequest.of(0, 6);
        List<CodingTest> codingTestList = new ArrayList<>();
        Page<CodingTest> codingTestPage = new PageImpl<>(codingTestList, pageable, codingTestList.size());

        when(codingTestRepository.dynamicSearching(problemLevel, problemAccuracy, status, pageable))
            .thenReturn(codingTestPage);

        // when
        Page<CodingTest> resultPage = codingTestService.dynamicSearching(problemLevel, problemAccuracy, status,
            pageable);

        // then
        assertAll(() -> assertEquals(codingTestPage, resultPage),
            () -> verify(codingTestRepository).dynamicSearching(problemLevel, problemAccuracy, status, pageable));
    }

    private CodingTest createCodingTest(String problemContents) {
        codingTest = CodingTest.createCodingTest(problemContents);
        em.persist(codingTest);
        return codingTest;
    }

    private CodingTest createNormalLevel() {
        CodingTest codingTest = CodingTest.createCodingTest("NormalName", "NormalSlug", "Normal",
            50.7, "NormalContents", null, null, null);
        em.persist(codingTest);
        return codingTest;
    }

    private CodingTest createEasyLevel() {
        CodingTest codingTest = CodingTest.createCodingTest("EasyName", "EasySlug", "Easy",
            50.7, "EasyContents", null, null, null);
        em.persist(codingTest);
        return codingTest;
    }
}