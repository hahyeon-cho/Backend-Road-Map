package ncnk.make.backendroadmap.domain.repository;

import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.Solved;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolvedRepository extends JpaRepository<Solved, Long> {
    Page<Solved> findSolvedByMember(Member member, Pageable pageable);
}
