package ncnk.make.backendroadmap.domain.repository;

import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.PracticeCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PracticeCodeRepository extends JpaRepository<PracticeCode, Long> {
    Page<PracticeCode> findPracticeCodesByMember(Member member, Pageable pageable);
}
