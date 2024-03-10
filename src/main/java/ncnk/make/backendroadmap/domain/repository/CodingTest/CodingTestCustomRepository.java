package ncnk.make.backendroadmap.domain.repository.CodingTest;

import java.util.List;
import ncnk.make.backendroadmap.domain.entity.CodingTest;

public interface CodingTestCustomRepository {
    List<CodingTest> findCsProblems(); //랜덤으로 코딩 테스트 문제 찾기 (비효율)

    List<CodingTest> findRandomProblemsByLevel(String level, int limit); //랜덤으로 코딩 테스트 문제 찾기
}
