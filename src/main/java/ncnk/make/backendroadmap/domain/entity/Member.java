package ncnk.make.backendroadmap.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ncnk.make.backendroadmap.domain.common.BaseTimeEntity;
import ncnk.make.backendroadmap.domain.exception.ResourceNotFoundException;

/**
 * 회원 테이블
 */
@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId; //PK
    private String profile; //프로필 사진 (저장 경로)
    private String email; //이메일
    private String name; //이름
    private String nickName; //닉네임
    private String github; //깃허브 주소
    private int level; //대분류 레벨
    private int point; //코딩테스트 점수
    private int hard; // 상 문제
    private int normal; // 중 문제
    private int easy; // 하 문제

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "main_docs_id")
    private MainCategory mainCategory; //대분류 FK

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // 로그인 시 유저 권한

    //생성자
    private Member(String profile, String email, String name, String nickName, String github, int level, int point,
                   Role role, int hard, int normal, int easy) {
        this.profile = profile;
        this.email = email;
        this.name = name;
        this.nickName = nickName;
        this.github = github;
        this.level = level;
        this.point = point;
        this.role = role;
        this.hard = hard;
        this.normal = normal;
        this.easy = easy;
    }

    //정적 팩토리 메서드 방식을 적용한 생성자
    public static Member createMember(String profile, String email, String name, String nickName, String github,
                                      int level, int point, Role role, int hard, int normal, int easy) {
        return new Member(profile, email, name, nickName, github, level, point, role, hard, normal, easy);
    }

    //회원 정보 업데이트
    public Member updateMember(String profile, String nickName, String github) {
        this.profile = profile;
        this.nickName = nickName;
        this.github = github;

        return this;
    }

    //Role Getter
    public String getRoleKey() {
        return this.role.getKey();
    }

    // 상/중/하 레벨을 찾고 해당 포인트를 더하는 메서드
    public void calculatePoint(String problemLevel) {
        Problem problem = Problem.getProblem(problemLevel);
        point += problem.getPoint();
    }

    // 멤버 대분류 레벨업
    public void updateLevel() {
        this.level += 1;
    }

    public void updateSolvedProblemsCount(String problemLevel) {
        if (problemLevel.equals(Problem.HARD.getProblemLevel())) {
            this.hard += 1;
        } else if (problemLevel.equals(Problem.NORMAL.getProblemLevel())) {
            this.normal += 1;
        } else if (problemLevel.equals(Problem.EASY.getProblemLevel())) {
            this.easy += 1;
        } else {
            throw new ResourceNotFoundException();
        }
    }
}