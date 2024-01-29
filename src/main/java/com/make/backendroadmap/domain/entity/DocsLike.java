package com.make.backendroadmap.domain.entity;

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
public class DocsLike {
    @Id
    @GeneratedValue
    @Column(name = "like_id")
    private Long likeId;

    @ManyToOne
    @JoinColumn(name = "sub_docs_id")
    private SubCategory subCategory;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;


}
