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
import java.util.List;
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

            List<Main> orderedMainDocs = Main.getOrderedMainDocs();
            for (Main orderedMainDoc : orderedMainDocs) {
                MainCategory mainCategory = MainCategory.createMainCategory(orderedMainDoc, orderedMainDoc.getUrl());
                em.persist(mainCategory);

                List<Sub> orderedSubDocsInCategory = Sub.getOrderedSubDocsInCategory(mainCategory.getMainDocsOrder());

                for (Sub sub : orderedSubDocsInCategory) {
                    SubCategory subCategory = SubCategory.createSubCategory(sub, 0L,
                            mainCategory); //TODO : constant likeCount
                    em.persist(subCategory);

                    DocsLike docsLike = DocsLike.createDocsLike(subCategory, member);
                    em.persist(docsLike);
                }

                Quiz quiz = Quiz.createQuiz("quizName", "quizContext", "image", "quizAnswer", mainCategory);
                em.persist(quiz);
            }
        }
    }
}

