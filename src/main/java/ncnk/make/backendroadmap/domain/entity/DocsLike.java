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

/**
 * 소분류 좋아요 테이블
 */
@Entity
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DocsLike {
    @Id
    @GeneratedValue
    @Column(name = "like_id")
    private Long likeId; //PK

    @ManyToOne
    @JoinColumn(name = "sub_docs_id")
    private SubCategory subCategory; //소분류 FK

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; //회원 Fk

    //생성자
    private DocsLike(SubCategory subCategory, Member member) {
        this.subCategory = subCategory;
        this.member = member;
    }

    //정적 팩토리 메서드 방식을 적용한 생성자
    public static DocsLike createDocsLike(SubCategory subCategory, Member member) {
        return new DocsLike(subCategory, member);
    }
}
