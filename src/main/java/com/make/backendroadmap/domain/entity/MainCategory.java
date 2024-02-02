package com.make.backendroadmap.domain.entity;

import com.make.backendroadmap.domain.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MainCategory extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "main_docs_id")
    private Long mainDocsId;

    @Enumerated(EnumType.STRING)
    private Main mainDocsTitle;

//    @OneToMany(mappedBy = "subDocsId", cascade = CascadeType.ALL)
//    private List<SubCategory> subCategories = new ArrayList<>();


    private MainCategory(Main mainDocsTitle) {
        this.mainDocsTitle = mainDocsTitle;
    }

    public static MainCategory createMainCategory(Main mainDocsTitle) {
        return new MainCategory(mainDocsTitle);
    }
}
