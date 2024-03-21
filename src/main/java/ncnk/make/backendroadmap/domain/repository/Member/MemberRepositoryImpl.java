package ncnk.make.backendroadmap.domain.repository.Member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.QMember;
import org.springframework.stereotype.Repository;

/**
 * QueryDsl 라이브러리를 이용한 Query 작성
 */
@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberCustomRepository {
    private final JPAQueryFactory queryFactory;

    // 포인트 랭킹 Top 5
    @Override
    public List<Member> top5Point() {
        return queryFactory
                .selectFrom(QMember.member)
                .orderBy(QMember.member.point.desc(), QMember.member.createdDate.desc())
                .limit(5)
                .fetch();
    }
}
