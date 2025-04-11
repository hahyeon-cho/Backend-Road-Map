package ncnk.make.backendroadmap.domain.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.aop.time.callback.TraceTemplate;
import ncnk.make.backendroadmap.domain.entity.CodingTest;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.Problem;
import ncnk.make.backendroadmap.domain.entity.Solved;
import ncnk.make.backendroadmap.domain.repository.solved.SolvedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
class SolvedServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private TraceTemplate template;

    @Mock
    private SolvedRepository solvedRepository; // 모의 객체 생성

    @InjectMocks
    private SolvedService solvedService; // 실제 객체에 모의 객체 주입

    private Member member;
    private CodingTest codingTest;
    private Solved solved;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        codingTest = mock(CodingTest.class);
        member = mock(Member.class);
        solved = createSolved(codingTest, member);
        solvedService = new SolvedService(solvedRepository, template);
    }

    @DisplayName("코딩 테스트 풀이 여부에 따라 포인트 더하는 로직")
    @Test
    void solvedProblemTest() {
        // given
        when(codingTest.getProblemLevel()).thenReturn(Problem.NORMAL.getProblemLevel());
        when(solvedRepository.findSolvedByCodingTestAndMember(codingTest, member))
            .thenReturn(Optional.of(solved));

        // when
        Optional<Solved> result = solvedService.solvedProblem(codingTest, member);

        // then
        assertAll(() -> assertTrue(result.isPresent()),
            () -> verify(member, times(1)).updateSolvedProblemsCount(Problem.NORMAL.getProblemLevel()),
            () -> verify(member, times(1)).calculatePoint(Problem.NORMAL.getProblemLevel()));
    }

    @DisplayName("처음 시도한 문제의 경우 solved 테이블에 컬럼을 추가한다.")
    @Test
    public void recordAttemptedProblem_WhenProblemIsNew_ShouldSaveAndReturnSolved() {
        // given
        when(solvedRepository.existsByCodingTestAndMember(codingTest, member)).thenReturn(false);

        // when
        Optional<Solved> result = solvedService.recordAttemptedProblem(codingTest, member, false);

        // then
        assertAll(() -> assertTrue(result.isPresent()),
            () -> verify(solvedRepository, times(1)).save(result.get()));
    }

    @DisplayName("이미 시도한 문제의 경우 solved 테이블에 컬럼을 추가 되지 않는다.")
    @Test
    void recordAttemptedProblem_WhenProblemAlreadyExists_ShouldReturnEmpty() {
        // given
        when(solvedRepository.existsByCodingTestAndMember(codingTest, member)).thenReturn(true);

        // when
        Optional<Solved> result = solvedService.recordAttemptedProblem(codingTest, member, true);

        // then
        assertFalse(result.isPresent());
    }


    @DisplayName("마이페이지: MyTest(동적 조회)")
    @ParameterizedTest(name = "{index} {displayName} arguments = {arguments}")
    @CsvSource({"Hard, desc, true", "Normal, desc, true", "Easy, asc, false"})
    void dynamicSearchingTest(String difficulty, String order, Boolean problemSolved) {
        // given
        Pageable pageable = PageRequest.of(0, 6);
        List<Solved> solvedList = new ArrayList<>();
        Page<Solved> solvedPage = new PageImpl<>(solvedList, pageable, solvedList.size());

        when(solvedRepository.dynamicSearching(difficulty, order, problemSolved, pageable)).thenReturn(solvedPage);

        // when
        Page<Solved> resultPage = solvedService.dynamicSearching(difficulty, order, problemSolved, pageable);

        // then
        assertAll(() -> assertEquals(solvedPage, resultPage),
            () -> verify(solvedRepository).dynamicSearching(difficulty, order, problemSolved, pageable));
    }


    private Solved createSolved(CodingTest codingTest, Member member) {
        Solved solved = Solved.createSolved(codingTest, member, false, "path");
        em.persist(solved);
        return solved;
    }
}