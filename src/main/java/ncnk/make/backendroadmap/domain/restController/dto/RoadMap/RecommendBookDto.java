package ncnk.make.backendroadmap.domain.restController.dto.RoadMap;

import lombok.Getter;
import ncnk.make.backendroadmap.domain.entity.RecommendBook;

@Getter
public class RecommendBookDto {

    private String bookTitle;
    private String bookAuthor;
    private String bookImage;
    private String publisher;

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
