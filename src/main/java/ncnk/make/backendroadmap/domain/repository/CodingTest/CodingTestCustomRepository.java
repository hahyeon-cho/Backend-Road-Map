package ncnk.make.backendroadmap.domain.repository.CodingTest;

import java.util.List;
import ncnk.make.backendroadmap.domain.entity.CodingTest;

public interface CodingTestCustomRepository {
    List<CodingTest> findCsProblems(CodingTest codingTest); //랜덤으로 코딩 테스트 문제 찾기
}
