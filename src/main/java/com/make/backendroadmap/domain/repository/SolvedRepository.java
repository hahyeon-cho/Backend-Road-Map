package com.make.backendroadmap.domain.repository;

import com.make.backendroadmap.domain.entity.Member;
import com.make.backendroadmap.domain.entity.Solved;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolvedRepository extends JpaRepository<Solved, Long> {
    List<Solved> findSolvedByMember(Member member);
}
