package ncnk.make.backendroadmap.domain.repository;


import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.PracticeCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 코딩 테스트 테이블 Repository (Spring-data-JPA 이용)
 */
public interface PracticeCodeRepository extends JpaRepository<PracticeCode, Long> {

    // 회원이 저장한 웹 컴파일러 정보를 Page로 반환
    Page<PracticeCode> findPracticeCodesByMember(Member member, Pageable pageable);
}
