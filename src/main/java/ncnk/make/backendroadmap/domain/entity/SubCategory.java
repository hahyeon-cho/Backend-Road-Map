package ncnk.make.backendroadmap.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
 * 소분류 테이블
 */
@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SubCategory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_docs_id")
    private Long subDocsId; //PK

    @Enumerated(EnumType.STRING)
    private Sub subDocsTitle; //소분류 정보

    private Long likeCount; //누적 좋아요 개수

    private String subDescription; //소분류 한줄 설명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_docs_id")
    private MainCategory mainCategory; //대분류 FK

    //생성자
    private SubCategory(Sub subDocsTitle, Long likeCount, String subDescription, MainCategory mainCategory) {
        this.subDocsTitle = subDocsTitle;
        this.likeCount = likeCount;
        this.subDescription = subDescription;
        this.mainCategory = mainCategory;
    }

    //정적 팩토리 메서드 방식을 적용한 생성자
    public static SubCategory createSubCategory(Sub subDocsTitle, Long likeCount, String subDescription,
                                                MainCategory mainCategory) {
        return new SubCategory(subDocsTitle, likeCount, subDescription, mainCategory);
    }
}