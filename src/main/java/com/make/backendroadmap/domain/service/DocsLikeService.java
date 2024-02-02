package com.make.backendroadmap.domain.service;

import com.make.backendroadmap.domain.controller.dto.Like.DocsLikeResponseDto;
import com.make.backendroadmap.domain.entity.DocsLike;
import com.make.backendroadmap.domain.entity.Member;
import com.make.backendroadmap.domain.entity.SubCategory;
import com.make.backendroadmap.domain.exception.DuplicateResourceException;
import com.make.backendroadmap.domain.exception.ResourceNotFoundException;
import com.make.backendroadmap.domain.repository.DocsLikeRepository;
import com.make.backendroadmap.domain.repository.MemberRepository;
import com.make.backendroadmap.domain.repository.SubCategory.SubCategoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public void insertSubCategoryLike(DocsLikeResponseDto docsLikeResponseDto) {
        SubCategory subCategory = getSubCategory(docsLikeResponseDto);

        Member member = getMember(docsLikeResponseDto);

        if (docsLikeRepository.findDocsLikeByMemberAndSubCategory(member, subCategory).isPresent()) {
            throw new DuplicateResourceException("[ERROR] Already Exist Like!! MemberId: " + member.getMemberId()
                    + "SubCategoryId: " + subCategory.getSubDocsId());
        }

        DocsLike docsLike = DocsLike.createDocsLike(subCategory, member);
        docsLikeRepository.save(docsLike);
        subCategoryRepository.addLikeCount(subCategory); //TODO 소분류 좋아요 개수 ++
    }

    @Transactional
    public void deleteSubCategoryLike(DocsLikeResponseDto docsLikeResponseDto) {
        SubCategory subCategory = getSubCategory(docsLikeResponseDto);

        Member member = getMember(docsLikeResponseDto);

        DocsLike docsLike = docsLikeRepository.findDocsLikeByMemberAndSubCategory(member, subCategory)
                .orElseThrow(() -> new ResourceNotFoundException());

        docsLikeRepository.delete(docsLike);
        subCategoryRepository.subLikeCount(subCategory); //TODO 소분류 좋아요 개수 --
    }

    public List<SubCategory> findSubCategoriesByMember(Member member) {
        return docsLikeRepository.findDocsLikesByMember(member).stream()
                .map(DocsLike::getSubCategory)
                .collect(Collectors.toList());
    }

    private Member getMember(DocsLikeResponseDto docsLikeResponseDto) {
        return memberRepository.findMemberByMemberId(docsLikeResponseDto.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException());
    }

    private SubCategory getSubCategory(DocsLikeResponseDto docsLikeResponseDto) {
        return subCategoryRepository.findSubCategoryBySubDocsId(docsLikeResponseDto.getSubDocsId())
                .orElseThrow(() -> new ResourceNotFoundException());
    }
}
