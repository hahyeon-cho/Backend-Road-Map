package ncnk.make.backendroadmap.domain.repository.CodingTest;

import static ncnk.make.backendroadmap.domain.entity.QCodingTest.codingTest;
import static ncnk.make.backendroadmap.domain.entity.QSolved.solved;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import ncnk.make.backendroadmap.domain.entity.CodingTest;
import ncnk.make.backendroadmap.domain.entity.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
                .selectFrom(codingTest)
                .join(codingTest, solved.codingTest).fetchJoin()
                .where(codingTest.problemLevel.eq(Problem.NORMAL.getProblemLevel())
                        .and(solved.problemSolved.eq(false)))
                .fetch();

        List<CodingTest> easyProblems = queryFactory
                .selectFrom(codingTest)
                .join(codingTest, solved.codingTest).fetchJoin()
                .where(codingTest.problemLevel.eq(Problem.EASY.getProblemLevel())
                        .and(solved.problemSolved.eq(false)))
                .fetch();

        Collections.shuffle(normalProblems);
        Collections.shuffle(easyProblems);

        List<CodingTest> result = new ArrayList<>();
        result.add(normalProblems.get(0));
        result.addAll(easyProblems.subList(0, 2));

        return result;
    }

    @Override
    public List<CodingTest> findRandomProblemsByLevel(String level, int limit) {
        return queryFactory
                .select(codingTest)
                .from(solved)
                .join(solved.codingTest, codingTest).fetchJoin()
                .where(solved.problemSolved.eq(false)
                        .and(solved.codingTest.problemLevel.eq(level)))
                .limit(limit)
                .fetch();
    }

    @Override
    public Page<CodingTest> dynamicSearching(String problemLevel, String problemAccuracy, String status,
                                             Pageable pageable) {

        QueryResults<CodingTest> results = queryFactory
                .selectFrom(codingTest)
                .where(problemLevelEq(problemLevel),
                        statusEq(status)
                )
                .orderBy(orderByProblemAccuracy(problemAccuracy))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    //검색 정보 중 "난이도(상/중/하)"가 있으면 적용, 없으면 무시
    private BooleanExpression problemLevelEq(String problemLevel) {
        if (problemLevel == null || problemLevel.isEmpty()) {
            return null;
        }
        return codingTest.problemLevel.eq(problemLevel);
    }

    //검색 정보 중 "정렬 기준(오름/내림차순)"가 있으면 적용, 없으면 내림차순 적용
    private OrderSpecifier<Double> orderByProblemAccuracy(String order) {
        if ("asc".equalsIgnoreCase(order)) {
            return codingTest.problemAccuracy.asc();
        }
        return codingTest.problemAccuracy.desc();
    }

    private BooleanExpression statusEq(String status) {
        if (status == null || status.isEmpty()) {
            return null;
        }
        if (status.equals("solved")) {
            return codingTest.codingTestId.in(
                    JPAExpressions
                            .select(solved.codingTest.codingTestId)
                            .from(solved)
                            .where(solved.problemSolved.eq(true))
            );
        } else if (status.equals("unsolved")) {
            return codingTest.codingTestId.notIn(
                    JPAExpressions
                            .select(solved.codingTest.codingTestId)
                            .from(solved)
            );
        } else if (status.equals("incorrect")) {
            return codingTest.codingTestId.in(
                    JPAExpressions
                            .select(solved.codingTest.codingTestId)
                            .from(solved)
                            .where(solved.problemSolved.eq(false))
            );
        }
        return null;
    }
}