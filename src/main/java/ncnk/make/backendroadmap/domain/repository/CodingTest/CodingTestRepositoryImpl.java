package ncnk.make.backendroadmap.domain.repository.CodingTest;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import ncnk.make.backendroadmap.domain.entity.CodingTest;
import ncnk.make.backendroadmap.domain.entity.Problem;
import ncnk.make.backendroadmap.domain.entity.QCodingTest;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CodingTestRepositoryImpl implements CodingTestCustomRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 비효율적인 코드 - 랜덤 값 추출을 위해 Normal / Easy 데이터를 모두 Select함!
     */
    @Override
    public List<CodingTest> findCsProblems(CodingTest codingTest) {
        List<CodingTest> normalProblems = queryFactory
                .selectFrom(QCodingTest.codingTest)
                .where(QCodingTest.codingTest.problemLevel.eq(Problem.NORMAL.getProblemLevel()))
                .fetch();

        List<CodingTest> easyProblems = queryFactory
                .selectFrom(QCodingTest.codingTest)
                .where(QCodingTest.codingTest.problemLevel.eq(Problem.EASY.getProblemLevel()))
                .fetch();

        Collections.shuffle(normalProblems);
        Collections.shuffle(easyProblems);

        List<CodingTest> result = new ArrayList<>();
        result.add(normalProblems.get(0));
        result.addAll(easyProblems.subList(0, 2));

        return result;
    }
}
