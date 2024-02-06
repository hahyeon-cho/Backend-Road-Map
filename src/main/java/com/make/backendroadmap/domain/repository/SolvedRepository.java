package com.make.backendroadmap.domain.repository;

import com.make.backendroadmap.domain.entity.Member;
import com.make.backendroadmap.domain.entity.Solved;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolvedRepository extends JpaRepository<Solved, Long> {
    Page<Solved> findSolvedByMember(Member member, Pageable pageable);
}
