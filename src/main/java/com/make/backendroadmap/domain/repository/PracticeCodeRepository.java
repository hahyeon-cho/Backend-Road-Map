package com.make.backendroadmap.domain.repository;

import com.make.backendroadmap.domain.entity.Member;
import com.make.backendroadmap.domain.entity.PracticeCode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PracticeCodeRepository extends JpaRepository<PracticeCode, Long> {
    List<PracticeCode> findPracticeCodesByMember(Member member);
}
