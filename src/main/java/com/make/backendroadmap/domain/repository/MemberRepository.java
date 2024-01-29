package com.make.backendroadmap.domain.repository;

import com.make.backendroadmap.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
