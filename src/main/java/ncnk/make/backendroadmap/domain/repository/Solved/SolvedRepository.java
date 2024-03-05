package ncnk.make.backendroadmap.domain.repository.Solved;

import ncnk.make.backendroadmap.domain.entity.Solved;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 코딩 테스트 풀이 여부 테이블 Repository (Spring-data-JPA 이용)
 */
public interface SolvedRepository extends JpaRepository<Solved, Long>, SolvedCustomRepository {
}
