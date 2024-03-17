package ncnk.make.backendroadmap.domain.repository.Member;

import java.util.Optional;
import ncnk.make.backendroadmap.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 회원 테이블 Repository (Spring-data-JPA 이용)
 */
public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {
    Optional<Member> findMemberByEmail(String email); //이메일 정보를 통해 회원 검색

    Optional<Member> findMemberByMemberId(Long id); //회원 PK를 통해 회원 검색
}
