package ncnk.make.backendroadmap.domain.repository.Solved;

import ncnk.make.backendroadmap.domain.entity.Solved;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 코딩 테스트 풀이 여부 테이블 Repository
 */
public interface SolvedCustomRepository {
    Page<Solved> dynamicSearching(String difficulty, String order, Boolean problemSolved,
                                  Pageable pageable); //마이페이지의 페이징 기능
}
