package ncnk.make.backendroadmap.domain.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.aop.time.callback.TraceTemplate;
import ncnk.make.backendroadmap.domain.entity.DocsLike;
import ncnk.make.backendroadmap.domain.entity.Main;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.Role;
import ncnk.make.backendroadmap.domain.entity.Sub;
import ncnk.make.backendroadmap.domain.entity.SubCategory;
import ncnk.make.backendroadmap.domain.repository.DocsLikeRepository;
import ncnk.make.backendroadmap.domain.repository.SubCategory.SubCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@Slf4j
class DocsLikeServiceTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    private DocsLikeService docsLikeService; // 실제 객체에 모의 객체 주입

    @Mock
    private DocsLikeRepository docsLikeRepository; // 모의 객체 생성

    @Autowired
    private TraceTemplate template;

    @Mock
    private SubCategoryRepository subCategoryRepository;

    private Member member;
    private MainCategory mainCategory;
    private SubCategory subCategory;
    private DocsLike docsLike;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        member = createMember();
        mainCategory = createMainCategory();
        docsLikeService = new DocsLikeService(subCategoryRepository, docsLikeRepository, template);
    }

    @DisplayName("소분류 좋아요 삭제")
    @ParameterizedTest(name = "{index} {displayName} arguments = {arguments}")
    @ValueSource(strings = {"IP", "TCP_UDP", "PORT", "DNS", "HTTP"})
    void givenLikeExists_whenToggleSubCategoryLike_thenDeleteLike(Sub sub) {
        //given
        subCategory = createSubCategory(sub, mainCategory);
        docsLike = createDocsLike();
        log.info("getSubDocsId= {}", docsLike.getSubCategory().getSubDocsId());
        when(docsLikeRepository.findDocsLikeByMemberAndSubCategory(member, subCategory))
                .thenReturn(Optional.of(docsLike));

        //when
        docsLikeService.toggleSubCategoryLike(member, subCategory);

        //then
        ArgumentCaptor<DocsLike> docsLikeCaptor = ArgumentCaptor.forClass(DocsLike.class);
        assertAll(() -> verify(docsLikeRepository).delete(docsLikeCaptor.capture()),
                () -> verify(subCategoryRepository).subLikeCount(subCategory),
                () -> assertEquals(docsLike.getSubCategory(), docsLikeCaptor.getValue().getSubCategory()),
                () -> assertEquals(docsLike.getMember(), docsLikeCaptor.getValue().getMember()));
    }

    @DisplayName("소분류 좋아요 추가")
    @ParameterizedTest(name = "{index} {displayName} arguments = {arguments}")
    @ValueSource(strings = {"IP", "TCP_UDP", "PORT", "DNS", "HTTP"})
    void givenLikeDoesNotExist_whenToggleSubCategoryLike_thenAddLike(Sub sub) {
        // given
        subCategory = createSubCategory(sub, mainCategory);
        docsLike = createDocsLike();

        // when
        docsLikeService.toggleSubCategoryLike(member, subCategory);

        // then
        ArgumentCaptor<DocsLike> docsLikeCaptor = ArgumentCaptor.forClass(DocsLike.class);
        assertAll(() -> verify(docsLikeRepository).save(docsLikeCaptor.capture()),
                () -> verify(subCategoryRepository).addLikeCount(subCategory),
                () -> assertEquals(docsLike.getSubCategory(), docsLikeCaptor.getValue().getSubCategory()),
                () -> assertEquals(docsLike.getMember(), docsLikeCaptor.getValue().getMember()));
    }

    @DisplayName("마이페이지: MyRoadMap")
    @Test
    void findAllByMemberTest() {
        //given
        Pageable pageable = PageRequest.of(0, 6);
        List<DocsLike> DocsLikes = new ArrayList<>();
        Page<DocsLike> DocsLikePage = new PageImpl<>(DocsLikes, pageable, DocsLikes.size());

        when(docsLikeRepository.findAllByMember(member, pageable)).thenReturn(DocsLikePage);

        //when
        docsLikeService.findAllByMember(member, pageable);

        //then
        verify(docsLikeRepository).findAllByMember(member, pageable); // 메소드 호출 검증
    }

    private Member createMember() {
        Member member = Member.createMember("profile", "email1", "name", "nickname", "github", 1, 0, Role.GUEST);
        em.persist(member);
        return member;
    }

    private MainCategory createMainCategory() {
        MainCategory mainCategory = MainCategory.createMainCategory(Main.INTERNET, Main.INTERNET.getUrl());
        em.persist(mainCategory);
        return mainCategory;
    }

    private SubCategory createSubCategory(Sub sub, MainCategory mainCategory) {
        SubCategory subCategory = SubCategory.createSubCategory(sub, 0L, sub.getSubDescription(), mainCategory);
        em.persist(subCategory);
        return subCategory;
    }

    private DocsLike createDocsLike() {
        docsLike = DocsLike.createDocsLike(subCategory, member);
        em.persist(docsLike);
        return docsLike;
    }
}