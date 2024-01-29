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
public class Solved {
    @Id
    @GeneratedValue
    @Column(name = "solved_id")
    private Long solvedId;

    @ManyToOne
    @JoinColumn(name = "codingTest_id")
    private CodingTest codingTest;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;

    private Boolean problemSolved;

    private String problemPath;

}
