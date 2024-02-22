package ncnk.make.backendroadmap.domain.repository.SubCategory;

import java.util.List;
import ncnk.make.backendroadmap.domain.entity.SubCategory;

public interface SubCategoryCustomRepository {
    List<SubCategory> addLikeCount(SubCategory subCategory);

    List<SubCategory> subLikeCount(SubCategory subCategory);
}
