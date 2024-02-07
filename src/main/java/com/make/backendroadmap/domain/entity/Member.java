package com.make.backendroadmap.domain.entity;

import com.make.backendroadmap.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long memberId;

    private String profile;
    private String email;
    private String name;
    private String github;
    private int level;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "main_docs_id")
    private MainCategory mainCategory;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    private Member(String profile, String email, String name, String github, int level, Role role) {
        this.profile = profile;
        this.email = email;
        this.name = name;
        this.github = github;
        this.level = level;
        this.role = role;
    }

    public static Member createMember(String profile, String email, String name, String github, int level, Role role) {
        return new Member(profile, email, name, github, level, role);
    }

    public Member updateMember(String profile, String name, String github) {
        this.profile = profile;
        this.name = name;
        this.github = github;

        return this;
    }

    public String getRoleKey() {
        return  this.role.getKey();
    }
}
