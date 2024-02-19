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

@Entity
@Getter
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
    @JoinColumn(name = "member_id")
    private Member member;

    private DocsLike(SubCategory subCategory, Member member) {
        this.subCategory = subCategory;
        this.member = member;
    }

    public static DocsLike createDocsLike(SubCategory subCategory, Member member) {
        return new DocsLike(subCategory, member);
    }
}
