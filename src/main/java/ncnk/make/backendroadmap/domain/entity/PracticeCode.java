package ncnk.make.backendroadmap.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ncnk.make.backendroadmap.domain.common.BaseTimeEntity;

/**
 * 웹 컴파일러 테이블
 */
@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PracticeCode extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "practice_id")
    private Long practiceId; //PK
    private String fileName; //저장하는 파일 이름
    private String path; //저장 경로
    private String language; //언어

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; //회원 FK

    //생성자
    private PracticeCode(String fileName, String path, String language, Member member) {
        this.fileName = fileName;
        this.path = path;
        this.language = language;
        this.member = member;
    }

    //정적 팩토리 메서드 방식을 적용한 생성자
    public static PracticeCode createPracticeCode(String fileName, String path, String language, Member member) {
        return new PracticeCode(fileName, path, language, member);
    }
}
