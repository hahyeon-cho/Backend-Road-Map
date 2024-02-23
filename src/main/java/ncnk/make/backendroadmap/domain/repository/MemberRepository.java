package ncnk.make.backendroadmap.domain.repository;

import java.util.Optional;
import ncnk.make.backendroadmap.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByEmail(String email);

    Optional<Member> findMemberByMemberId(Long id);
}
