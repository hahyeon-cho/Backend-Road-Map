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
import ncnk.make.backendroadmap.domain.common.BaseTimeEntity;

@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Solved extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "solved_id")
    private Long solvedId;

    @ManyToOne
    @JoinColumn(name = "codingTest_id")
    private CodingTest codingTest;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private Boolean problemSolved;

    private String problemPath;

}
