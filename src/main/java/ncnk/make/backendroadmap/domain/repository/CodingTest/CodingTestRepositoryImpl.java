package ncnk.make.backendroadmap.domain.repository.CodingTest;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import ncnk.make.backendroadmap.domain.entity.CodingTest;
import ncnk.make.backendroadmap.domain.entity.Problem;
import ncnk.make.backendroadmap.domain.entity.QCodingTest;
import ncnk.make.backendroadmap.domain.entity.QSolved;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CodingTestRepositoryImpl implements CodingTestCustomRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 비효율적인 코드 - 랜덤 값 추출을 위해 Normal / Easy 데이터를 모두 Select함!
     */
    @Override
    public List<CodingTest> findCsProblems() {
        List<CodingTest> normalProblems = queryFactory
                .selectFrom(QCodingTest.codingTest)
                .join(QCodingTest.codingTest, QSolved.solved.codingTest)
                .where(QCodingTest.codingTest.problemLevel.eq(Problem.NORMAL.getProblemLevel())
                        .and(QSolved.solved.problemSolved.eq(false)))
                .fetch();

        List<CodingTest> easyProblems = queryFactory
                .selectFrom(QCodingTest.codingTest)
                .join(QCodingTest.codingTest, QSolved.solved.codingTest)
                .where(QCodingTest.codingTest.problemLevel.eq(Problem.EASY.getProblemLevel())
                        .and(QSolved.solved.problemSolved.eq(false)))
                .fetch();

        Collections.shuffle(normalProblems);
        Collections.shuffle(easyProblems);

        List<CodingTest> result = new ArrayList<>();
        result.add(normalProblems.get(0));
        result.addAll(easyProblems.subList(0, 2));

        return result;
    }


    public List<CodingTest> findRandomProblemsByLevel(String level, int limit) {
        QCodingTest ct = QCodingTest.codingTest;
        QSolved s = QSolved.solved;
        //TODO: Eager -> Lazy 이후 .fetchJoin()
        return queryFactory
                .select(ct)
                .from(s)
                .join(s.codingTest, ct)
                .where(s.problemSolved.eq(false)
                        .and(s.codingTest.problemLevel.eq(level)))
                .limit(limit)
                .fetch();
    }
}