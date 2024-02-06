package com.make.backendroadmap.domain.repository;

import com.make.backendroadmap.domain.entity.Member;
import com.make.backendroadmap.domain.entity.PracticeCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PracticeCodeRepository extends JpaRepository<PracticeCode, Long> {
    Page<PracticeCode> findPracticeCodesByMember(Member member, Pageable pageable);
}
