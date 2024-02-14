package com.make.backendroadmap.domain.repository.SubCategory;

import com.make.backendroadmap.domain.entity.SubCategory;
import java.util.List;

public interface SubCategoryCustomRepository {
    List<SubCategory> addLikeCount(SubCategory subCategory);

    List<SubCategory> subLikeCount(SubCategory subCategory);
}
