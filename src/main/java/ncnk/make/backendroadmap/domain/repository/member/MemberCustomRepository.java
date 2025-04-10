package ncnk.make.backendroadmap.domain.repository.member;

import java.util.List;
import ncnk.make.backendroadmap.domain.entity.Member;

/**
 * 코딩 테스트 풀이 여부 테이블 Repository
 */
public interface MemberCustomRepository {

    List<Member> top5Point();
}
