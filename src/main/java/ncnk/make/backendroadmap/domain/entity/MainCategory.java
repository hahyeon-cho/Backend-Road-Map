package ncnk.make.backendroadmap.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ncnk.make.backendroadmap.domain.common.BaseTimeEntity;
import ncnk.make.backendroadmap.domain.exception.ResourceNotFoundException;

/**
 * 대분류 테이블
 */
@Entity
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MainCategory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "main_docs_id")
    private Long mainDocsId; // PK

    @Enumerated(EnumType.STRING)
    private Main mainDocsTitle; // 대분류 정보

    private String mainDocsUrl; // 대분류 URL


    // 생성자
    private MainCategory(Main mainDocsTitle, String mainDocsUrl) {
        this.mainDocsTitle = mainDocsTitle;
        this.mainDocsUrl = mainDocsUrl;
    }

    // 정적 팩토리 메서드 방식을 적용한 생성자
    public static MainCategory createMainCategory(Main mainDocsTitle, String mainDocsUrl) {
        return new MainCategory(mainDocsTitle, mainDocsUrl);
    }

    // 몇 번째 대분류 정보인가?
    public int getMainDocsOrder() {
        return mainDocsTitle.getMainDocsOrder();
    }

    // 인자 값(mainDocs)와 같은 대분류 정보 찾기
    public MainCategory getMainCategory(String mainDoc) {
        Main main = Main.getInstance(mainDoc.trim());
        if (main.name().equals(mainDoc)) {
            return this;
        }
        throw new ResourceNotFoundException();
    }
}