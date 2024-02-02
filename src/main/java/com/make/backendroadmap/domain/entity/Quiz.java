package com.make.backendroadmap.domain.entity;

import com.make.backendroadmap.domain.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Quiz extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "quiz_id")
    private Long quizId;

    private String quizName;

    private String quizContext;

    private String quizImage;

    private String quizAnswer;

    @ManyToOne
    @JoinColumn(name = "main_docs_id")
    private MainCategory mainCategory;

    private Quiz(String quizName, String quizContext, String quizImage, String quizAnswer, MainCategory mainCategory) {
        this.quizName = quizName;
        this.quizContext = quizContext;
        this.quizImage = quizImage;
        this.quizAnswer = quizAnswer;
        this.mainCategory = mainCategory;
    }

    public static Quiz createQuiz(String quizName, String quizContext, String quizImage, String quizAnswer,
                                  MainCategory mainCategory) {
        return new Quiz(quizName, quizContext, quizImage, quizAnswer, mainCategory);
    }
}
