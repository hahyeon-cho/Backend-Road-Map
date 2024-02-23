package com.make.backendroadmap.domain.entity;

import com.make.backendroadmap.domain.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PracticeCode extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "practice_id")
    private Long practiceId;
    private String fileName;
    private String path;
    private String language;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private PracticeCode(String fileName, String path, String language, Member member) {
        this.fileName = fileName;
        this.path = path;
        this.language = language;
        this.member = member;
    }

    public static PracticeCode createPracticeCode(String fileName, String path, String language, Member member) {
        return new PracticeCode(fileName, path, language, member);
    }
}
