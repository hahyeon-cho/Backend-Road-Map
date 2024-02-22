package com.make.backendroadmap.domain.entity;

import com.make.backendroadmap.domain.common.BaseTimeEntity;
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
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long memberId;

    private String profile;
    private String email;
    private String name;
    private String nickName;
    private String github;
    private int level;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "main_docs_id")
    private MainCategory mainCategory;

    private Member(String profile, String email, String name, String nickName, String github, int level) {
        this.profile = profile;
        this.email = email;
        this.name = name;
        this.nickName = nickName;
        this.github = github;
        this.level = level;
    }

    public static Member createMember(String profile, String email, String name, String nickName, String github,
                                      int level) {
        return new Member(profile, email, name, nickName, github, level);
    }

    public Member updateMember(String profile, String name, String github) {
        this.profile = profile;
        this.name = name;
        this.github = github;

        return this;
    }
}
