package com.make.backendroadmap.domain.entity;

import com.make.backendroadmap.domain.common.BaseTimeEntity;
import com.make.backendroadmap.domain.exception.ResourceNotFoundException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MainCategory extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "main_docs_id")
    private Long mainDocsId;

    @Enumerated(EnumType.STRING)
    private Main mainDocsTitle;

    private String mainDocsUrl;


    private MainCategory(Main mainDocsTitle, String mainDocsUrl) {
        this.mainDocsTitle = mainDocsTitle;
        this.mainDocsUrl = mainDocsUrl;
    }

    public static MainCategory createMainCategory(Main mainDocsTitle, String mainDocsUrl) {
        return new MainCategory(mainDocsTitle, mainDocsUrl);
    }

    public int getMainDocsOrder() {
        return mainDocsTitle.getMainDocsOrder();
    }

    public MainCategory getMainCategory(String mainDoc) {
        Main main = Main.getInstance(mainDoc.trim());
        if (main.name().equals(mainDoc)) {
            return this;
        }
        throw new ResourceNotFoundException();
    }
}
