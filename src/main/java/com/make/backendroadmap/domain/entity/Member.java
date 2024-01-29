package com.make.backendroadmap.domain.entity;

import jakarta.persistence.CascadeType;
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
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long userId;

    private String profile;

    private String email;

    private String name;

    private String github;

    private int level;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "main_docs_id")
    private MainCategory mainCategory;

    public Member(Long userId, String profile, String email, String name, String github, int level,
                  MainCategory mainCategory) {
        this.userId = userId;
        this.profile = profile;
        this.email = email;
        this.name = name;
        this.github = github;
        this.level = level;
        this.mainCategory = mainCategory;
    }
}
