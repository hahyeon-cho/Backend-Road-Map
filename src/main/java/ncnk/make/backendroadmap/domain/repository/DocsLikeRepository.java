package ncnk.make.backendroadmap.domain.repository;

import java.util.List;
import java.util.Optional;
import ncnk.make.backendroadmap.domain.entity.DocsLike;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocsLikeRepository extends JpaRepository<DocsLike, Long> {
    Optional<DocsLike> findDocsLikeByMemberAndSubCategory(Member member, SubCategory subCategory);

    List<DocsLike> findDocsLikesByMember(Member member);

    Page<DocsLike> findAllByMember(Member member, Pageable pageable);
}
