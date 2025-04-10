package ncnk.make.backendroadmap.domain.repository.codingTest;

import java.util.List;
import ncnk.make.backendroadmap.domain.entity.CodingTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CodingTestCustomRepository {

    // 랜덤으로 코딩 테스트 문제 찾기 (비효율)
    List<CodingTest> findCsProblems();

    // 랜덤으로 코딩 테스트 문제 찾기
    List<CodingTest> findRandomProblemsByLevel(String level, int limit);

    // 문제 페이지 페이징
    Page<CodingTest> dynamicSearching(String problemLevel, String problemAccuracy, String status, Pageable pageable);
}
