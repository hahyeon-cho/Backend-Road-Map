package ncnk.make.backendroadmap.domain.repository;

import java.util.List;
import java.util.Optional;
import ncnk.make.backendroadmap.domain.entity.DocsLike;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 소분류 좋아요 테이블 Repository (Spring-data-JPA 이용)
 */
public interface DocsLikeRepository extends JpaRepository<DocsLike, Long> {
    Optional<DocsLike> findDocsLikeByMemberAndSubCategory(Member member,
                                                          SubCategory subCategory); //회원 & 소분류 정보를 이용해 소분류 좋아요 정보 반환

    List<DocsLike> findDocsLikesByMember(Member member); //회원 정보를 이용해 소분류 좋아요 정보를 List로 반환

    Page<DocsLike> findAllByMember(Member member, Pageable pageable); // 마이페이지에서 회원이 누른 소분류 좋아요 정보 모두 검색
}