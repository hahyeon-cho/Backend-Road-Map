package ncnk.make.backendroadmap.domain.restController.dto.roadMap;

import lombok.Getter;
import ncnk.make.backendroadmap.domain.entity.RecommendBook;

/**
 * 책 추천 Dto
 */
@Getter
public class RecommendBookDto {

    private String bookTitle; // 추천 책 제목
    private String bookAuthor; // 추천 책 저자
    private String bookImage; // 추천 책 이미지
    private String publisher; // 추천 책 출판사

    private RecommendBookDto(RecommendBook recommendBook) {
        this.bookTitle = recommendBook.getBookTitle();
        this.bookAuthor = recommendBook.getBookAuthor();
        this.bookImage = recommendBook.getBookImage();
        this.publisher = recommendBook.getPublisher();
    }

    public static RecommendBookDto createRecommendBookDto(RecommendBook recommendBook) {
        return new RecommendBookDto(recommendBook);
    }
}
