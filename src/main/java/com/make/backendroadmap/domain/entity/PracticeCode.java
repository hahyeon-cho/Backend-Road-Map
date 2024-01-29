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
public class PracticeCode {
    @Id
    @GeneratedValue
    @Column(name = "practice_id")
    private Long practiceId;
    private String code;
    private String language;
    private String practicePath;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;

}
