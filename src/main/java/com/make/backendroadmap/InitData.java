package com.make.backendroadmap;

import com.make.backendroadmap.domain.entity.DocsLike;
import com.make.backendroadmap.domain.entity.Main;
import com.make.backendroadmap.domain.entity.MainCategory;
import com.make.backendroadmap.domain.entity.Member;
import com.make.backendroadmap.domain.entity.Quiz;
import com.make.backendroadmap.domain.entity.Sub;
import com.make.backendroadmap.domain.entity.SubCategory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitData {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.init1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void init1() {
            Member member = Member.createMember("profile", "email", "name", "github", 1);
            em.persist(member);

            MainCategory algorithm = MainCategory.createMainCategory(Main.Algorithm);
            em.persist(algorithm);

            SubCategory subCategory = SubCategory.createSubCategory(Sub.BUBBLE_SORT, 1L, "sub_url", algorithm);
            em.persist(subCategory);

            DocsLike docsLike = DocsLike.createDocsLike(subCategory, member);
            em.persist(docsLike);

            Quiz quiz = Quiz.createQuiz("quizName", "quizContext", "image", "quizAnswer", algorithm);
            em.persist(quiz);
        }
    }
}

