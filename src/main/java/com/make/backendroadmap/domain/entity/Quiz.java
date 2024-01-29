package com.make.backendroadmap.domain.entity;

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
public class Quiz {
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
}
