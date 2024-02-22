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
public class RecommendBook extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "book_id")
    private Long bookId;

    private String bookTitle;

    private String bookAuthor;

    private String bookImage;

    private String publisher;

    @ManyToOne
    @JoinColumn(name = "main_docs_id")
    private MainCategory mainCategory;

    private RecommendBook(String bookTitle, String bookAuthor, String bookImage, String publisher,
                          MainCategory mainCategory) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookImage = bookImage;
        this.publisher = publisher;
        this.mainCategory = mainCategory;
    }

    public static RecommendBook createRecommend(String bookTitle, String bookAuthor, String bookImage, String publisher,
                                                MainCategory mainCategory) {
        return new RecommendBook(bookTitle, bookAuthor, bookImage, publisher, mainCategory);
    }
}
