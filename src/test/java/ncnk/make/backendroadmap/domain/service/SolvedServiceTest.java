package ncnk.make.backendroadmap.domain.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.Solved;
import ncnk.make.backendroadmap.domain.repository.Solved.SolvedRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

    @Mock
    private SolvedRepository solvedRepository; // 모의 객체 생성

    @InjectMocks
    private SolvedService solvedService; // 실제 객체에 모의 객체 주입

    @DisplayName("마이페이지: MyTest(동적 조회)")
    @ParameterizedTest(name = "{index} {displayName} arguments = {arguments}")
    @CsvSource({"Hard, desc, true", "Normal, desc, true", "Easy, asc, false"})
    void dynamicSearchingTest(String difficulty, String order, Boolean problemSolved) {
        //given
        Pageable pageable = PageRequest.of(0, 6);
        List<Solved> solvedList = new ArrayList<>();
        Page<Solved> solvedPage = new PageImpl<>(solvedList, pageable, solvedList.size());

        when(solvedRepository.dynamicSearching(difficulty, order, problemSolved, pageable)).thenReturn(solvedPage);

        //when
        solvedService.dynamicSearching(difficulty, order, problemSolved, pageable);

        //then
        verify(solvedRepository).dynamicSearching(difficulty, order, problemSolved, pageable); // 메소드 호출 검증
    }
}