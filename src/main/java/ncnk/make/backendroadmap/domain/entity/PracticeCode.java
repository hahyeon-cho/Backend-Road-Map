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
public class PracticeCode extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "practice_id")
    private Long practiceId;
    private String title;
    private String code;
    private String language;
    private String practicePath;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
