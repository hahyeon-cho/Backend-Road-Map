package ncnk.make.backendroadmap.domain.repository.SubCategory;

import java.util.List;
import ncnk.make.backendroadmap.domain.entity.SubCategory;

/**
 * 소분류 테이블 Repository
 */
public interface SubCategoryCustomRepository {
    List<SubCategory> addLikeCount(SubCategory subCategory); //인자 값(subCategory)에 해당하는 소분류 값의 좋아요 + 1

    List<SubCategory> subLikeCount(SubCategory subCategory); //인자 값(subCategory)에 해당하는 소분류 값의 좋아요 - 1
}
