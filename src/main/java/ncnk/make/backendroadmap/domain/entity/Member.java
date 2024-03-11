package ncnk.make.backendroadmap.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ncnk.make.backendroadmap.domain.common.BaseTimeEntity;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    private Member(String profile, String email, String name, String nickName, String github, int level, Role role) {
        this.profile = profile;
        this.email = email;
        this.name = name;
        this.nickName = nickName;
        this.github = github;
        this.level = level;
        this.role = role;
    }

    public static Member createMember(String profile, String email, String name, String nickName, String github, int level, Role role) {
        return new Member(profile, email, name, nickName, github, level, role);
    }

    public Member updateMember(String profile, String nickName, String github) {
        this.profile = profile;
        this.nickName = nickName;
        this.github = github;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}