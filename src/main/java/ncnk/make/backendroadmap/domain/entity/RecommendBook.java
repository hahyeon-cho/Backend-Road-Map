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

/**
 * 책 추천 테이블
 */
@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RecommendBook extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "book_id")
    private Long bookId; //PK

    private String bookTitle; //도서 제목

    private String bookAuthor; //저자

    private String bookImage; //책 표지

    private String publisher; // 출판사

    @ManyToOne
    @JoinColumn(name = "main_docs_id")
    private MainCategory mainCategory; //대분류 FK

    //생성자
    private RecommendBook(String bookTitle, String bookAuthor, String bookImage, String publisher,
                          MainCategory mainCategory) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookImage = bookImage;
        this.publisher = publisher;
        this.mainCategory = mainCategory;
    }

    //정적 팩토리 메서드 방식을 적용한 생성자
    public static RecommendBook createRecommend(String bookTitle, String bookAuthor, String bookImage, String publisher,
                                                MainCategory mainCategory) {
        return new RecommendBook(bookTitle, bookAuthor, bookImage, publisher, mainCategory);
    }
}
