package ncnk.make.backendroadmap.domain.repository;

import ncnk.make.backendroadmap.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByEmail(String email);

    Optional<Member> findMemberByMemberId(Long id);

    List<String> findNickNameByNickName(String nickName);
}
