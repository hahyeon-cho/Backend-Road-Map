package ncnk.make.backendroadmap.domain.repository.CodingTest;

import java.util.List;
import ncnk.make.backendroadmap.domain.entity.CodingTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CodingTestRepository extends JpaRepository<CodingTest, Long>, CodingTestCustomRepository {

    //MySQL 전용 JPQL
    @Query(nativeQuery = true, value = "SELECT * FROM coding_test WHERE problem_level = ?1 ORDER BY RAND() LIMIT ?2")
    List<CodingTest> findRandomProblemsByLevelMySQL(String level, int limit);

    //H2 DB 전용 JPQL
    @Query(nativeQuery = true, value = "SELECT TOP ?2 * FROM coding_test WHERE problem_level = ?1 ORDER BY RAND()")
    List<CodingTest> findRandomProblemsByLevelH2Db(String level, int limit);
}