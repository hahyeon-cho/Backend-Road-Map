package com.make.backendroadmap.domain.repository;

import com.make.backendroadmap.domain.entity.DocsLike;
import com.make.backendroadmap.domain.entity.Member;
import com.make.backendroadmap.domain.entity.SubCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocsLikeRepository extends JpaRepository<DocsLike, Long> {
    Optional<DocsLike> findDocsLikeByMemberAndSubCategory(Member member, SubCategory subCategory);

    List<DocsLike> findDocsLikesByMember(Member member);

    Page<DocsLike> findAllByMember(Member member, Pageable pageable);
}
