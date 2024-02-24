package ncnk.make.backendroadmap.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.DocsLike;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.SubCategory;
import ncnk.make.backendroadmap.domain.exception.ResourceNotFoundException;
import ncnk.make.backendroadmap.domain.repository.DocsLikeRepository;
import ncnk.make.backendroadmap.domain.repository.MemberRepository;
import ncnk.make.backendroadmap.domain.repository.SubCategory.SubCategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class DocsLikeService {
    private final MemberRepository memberRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final DocsLikeRepository docsLikeRepository;

    @Transactional
    public void toggleSubCategoryLike(Member member, SubCategory subCategory) {
        Optional<DocsLike> optionalLike = docsLikeRepository.findDocsLikeByMemberAndSubCategory(member, subCategory);

        if (optionalLike.isPresent()) {
            docsLikeRepository.delete(optionalLike.get());
            subCategoryRepository.subLikeCount(subCategory); //소분류 누적 좋아요 개수 --
        } else {
            DocsLike docsLike = DocsLike.createDocsLike(subCategory, member);
            docsLikeRepository.save(docsLike);
            subCategoryRepository.addLikeCount(subCategory); //소분류 누적 좋아요 개수 ++
        }
    }

    public DocsLike findDocsLikeByMemberAndSubCategory(Member member, SubCategory subCategory) {
        return docsLikeRepository.findDocsLikeByMemberAndSubCategory(member, subCategory)
                .orElseThrow(() -> new ResourceNotFoundException());
    }


    public List<SubCategory> findSubCategoriesByMember(Member member) {
        return docsLikeRepository.findDocsLikesByMember(member).stream()
                .map(DocsLike::getSubCategory)
                .collect(Collectors.toList());
    }

    public Page<DocsLike> findAllByMember(Member member, Pageable pageable) {
        return docsLikeRepository.findAllByMember(member, pageable);
    }

    private SubCategory getSubCategory(DocsLike docsLike) {
        SubCategory subCategory = subCategoryRepository.findSubCategoryBySubDocsId(
                        docsLike.getSubCategory().getSubDocsId())
                .orElseThrow(() -> new ResourceNotFoundException());
        return subCategory;
    }

    private Member getMember(DocsLike docsLike) {
        Member member = memberRepository.findMemberByMemberId(docsLike.getMember().getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException());
        return member;
    }
}
