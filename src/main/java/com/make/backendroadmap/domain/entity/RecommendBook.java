package com.make.backendroadmap.domain.entity;

import com.make.backendroadmap.domain.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendBook extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "book_id")
    private Long bookId;

    private String bookTitle;

    private String bookAuthor;

    private String bookImage;

    @ManyToOne
    @JoinColumn(name = "sub_docs_id")
    private SubCategory subCategory;

}
