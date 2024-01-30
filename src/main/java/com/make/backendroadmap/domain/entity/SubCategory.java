package com.make.backendroadmap.domain.entity;

import com.make.backendroadmap.domain.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SubCategory extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "sub_docs_id")
    private Long subDocsId;

    @Enumerated(EnumType.STRING)
    private Sub subDocsTitle;

    @ManyToOne
    @JoinColumn(name = "main_docs_id")
    private MainCategory mainCategory;


}
