package ncnk.make.backendroadmap.domain.repository.Solved;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import ncnk.make.backendroadmap.domain.entity.QMember;
import ncnk.make.backendroadmap.domain.entity.QSolved;
import ncnk.make.backendroadmap.domain.entity.Solved;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SolvedRepositoryImpl implements SolvedCustomRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 가져온 데이터를 Page 객체로 변환해 반환하도록 코드를 수정 -> 대용량 데이터에 부적합!
     */

//    @Override
//    public Page<Solved> dynamicSearching(String difficulty, String order, Boolean problemSolved,
//                                         Pageable pageable) {
//        List<Solved> fetch = queryFactory
//                .selectFrom(QSolved.solved)
//                .where(searchByDifficulty(difficulty), searchByProblemSolved(problemSolved))
//                .orderBy(orderByAccuracy(order))
//                .fetch();
//
//        int start = (int) pageable.getOffset();
//        int end = Math.min((start + pageable.getPageSize()), fetch.size());
//
//        return new PageImpl<>(fetch.subList(start, end), pageable, fetch.size());
//    }

    /**
     * 성능 최적화 버전
     */
    @Override
    public Page<Solved> dynamicSearching(String difficulty, String order, Boolean problemSolved,
                                         Pageable pageable) {
        QueryResults<Solved> results = queryFactory
                .selectFrom(QSolved.solved)
                .join(QSolved.solved.member, QMember.member).fetchJoin()
                .where(searchByDifficulty(difficulty), searchByProblemSolved(problemSolved))
                .orderBy(orderByAccuracy(order))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }
    
    private BooleanExpression searchByDifficulty(String difficulty) {
        if (difficulty == null) {
            return null;
        }
        return QSolved.solved.codingTest.problemDifficulty.contains(difficulty);
    }

    private OrderSpecifier<Double> orderByAccuracy(String order) {
        if ("desc".equalsIgnoreCase(order)) {
            return QSolved.solved.codingTest.problemAccuracy.desc();
        }
        return QSolved.solved.codingTest.problemAccuracy.asc(); //기본 값은 오름차순
    }

    private BooleanExpression searchByProblemSolved(Boolean problemSolved) {
        if (problemSolved == null) {
            return null;
        } else if (problemSolved == true) {
            return QSolved.solved.problemSolved.eq(true);
        }
        return QSolved.solved.problemSolved.eq(false);
    }
}
