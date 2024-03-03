package ncnk.make.backendroadmap.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 코딩 테스트 풀이 여부 테이블 CodingTest 테이블 변동에 따라 컬럼값 변할 수 있음!
 */
@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Solved {
    @Id
    @GeneratedValue
    @Column(name = "solved_id")
    private Long solvedId; //PK

    @ManyToOne
    @JoinColumn(name = "codingTest_id")
    private CodingTest codingTest; //코딩 테스트 FK

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; //회원 FK

    private Boolean problemSolved; //문제 풀이 여부

    private String problemPath; //제출 경로

    //생성자
    private Solved(CodingTest codingTest, Member member, Boolean problemSolved, String problemPath) {
        this.codingTest = codingTest;
        this.member = member;
        this.problemSolved = problemSolved;
        this.problemPath = problemPath;
    }

    //정적 팩토리 메서드 방식을 적용한 생성자
    public static Solved createSolved(CodingTest codingTest, Member member, Boolean problemSolved, String problemPath) {
        return new Solved(codingTest, member, problemSolved, problemPath);
    }


    public void checkSolved() {
        //TODO: 정답이 맞다면 problemSolved를 true로 바꾸는 로직
    }
}
