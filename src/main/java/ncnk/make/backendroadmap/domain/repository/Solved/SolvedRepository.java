package ncnk.make.backendroadmap.domain.repository.Solved;

import java.util.Optional;
import ncnk.make.backendroadmap.domain.entity.CodingTest;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.Solved;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 코딩 테스트 풀이 여부 테이블 Repository (Spring-data-JPA 이용)
 */
public interface SolvedRepository extends JpaRepository<Solved, Long>, SolvedCustomRepository {
    Optional<Solved> findSolvedByCodingTestAndMember(CodingTest codingTest, Member member); //문제 풀이 PK를 통해 해당 풀이 여부 검색

    boolean existsByCodingTestAndMember(CodingTest codingTest, Member member);
}
