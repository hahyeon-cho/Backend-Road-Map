package com.make.backendroadmap.domain.controller.dto.RoadMap;

import com.make.backendroadmap.domain.entity.RecommendBook;
import lombok.Getter;

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
