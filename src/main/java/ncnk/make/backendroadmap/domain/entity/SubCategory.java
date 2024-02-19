package ncnk.make.backendroadmap.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class SubCategory extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "sub_docs_id")
    private Long subDocsId;

    @Enumerated(EnumType.STRING)
    private Sub subDocsTitle;

    private Long likeCount;


    @ManyToOne
    @JoinColumn(name = "main_docs_id")
    private MainCategory mainCategory;

    private SubCategory(Sub subDocsTitle, Long likeCount, MainCategory mainCategory) {
        this.subDocsTitle = subDocsTitle;
        this.likeCount = likeCount;
        this.mainCategory = mainCategory;
    }

    public static SubCategory createSubCategory(Sub subDocsTitle, Long likeCount, MainCategory mainCategory) {
        return new SubCategory(subDocsTitle, likeCount, mainCategory);
    }
}
