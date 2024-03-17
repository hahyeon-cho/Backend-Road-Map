package ncnk.make.backendroadmap.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.aop.time.callback.TraceTemplate;
import ncnk.make.backendroadmap.domain.entity.DocsLike;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.SubCategory;
import ncnk.make.backendroadmap.domain.exception.ResourceNotFoundException;
import ncnk.make.backendroadmap.domain.repository.DocsLikeRepository;
import ncnk.make.backendroadmap.domain.repository.SubCategory.SubCategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 소분류 좋아요 Service (BIZ 로직)
 */
@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class DocsLikeService {
    private final SubCategoryRepository subCategoryRepository;
    private final DocsLikeRepository docsLikeRepository;
    private final TraceTemplate template;

    //토글 형식의 좋아요 버튼 (좋아요가 있다면 -1, 없다면 +1)
    @Transactional
    public Optional<DocsLike> toggleSubCategoryLike(Member member, SubCategory subCategory) {
        Optional<DocsLike> optionalLike = docsLikeRepository.findDocsLikeByMemberAndSubCategory(member, subCategory);

        if (optionalLike.isPresent()) {
            template.execute("DocsLikeService.toggleSubCategoryLike.delete()", () -> {
                docsLikeRepository.delete(optionalLike.get());
                subCategoryRepository.subLikeCount(subCategory); //소분류 누적 좋아요 개수 --
                return null;
            });
            return Optional.empty();
        } else {
            return Optional.of(template.execute("DocsLikeService.toggleSubCategoryLike.add()", () -> {
                DocsLike docsLike = DocsLike.createDocsLike(subCategory, member);
                docsLikeRepository.save(docsLike);
                subCategoryRepository.addLikeCount(subCategory); //소분류 누적 좋아요 개수 ++
                return docsLike;
            }));
        }
    }

    //회원 정보와 소분류 정보 이용해 소분류 좋아요 정보 조회
    public DocsLike findDocsLikeByMemberAndSubCategory(Member member, SubCategory subCategory) {
        return docsLikeRepository.findDocsLikeByMemberAndSubCategory(member, subCategory)
                .orElseThrow(() -> new ResourceNotFoundException());
    }

    //회원 정보 이용해 소분류 좋아요 정보 조회(List로 반환)
    public List<SubCategory> findSubCategoriesByMember(Member member) {
        return docsLikeRepository.findDocsLikesByMember(member).stream()
                .map(DocsLike::getSubCategory)
                .collect(Collectors.toList());
    }

    //회원 정보 이용해 소분류 좋아요 정보 조회(Page로 반환)
    public Page<DocsLike> findAllByMember(Member member, Pageable pageable) {
        return docsLikeRepository.findAllByMember(member, pageable);
    }
}
