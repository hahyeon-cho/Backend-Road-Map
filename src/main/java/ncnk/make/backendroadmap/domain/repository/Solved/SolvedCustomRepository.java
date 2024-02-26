package ncnk.make.backendroadmap.domain.repository.Solved;

import ncnk.make.backendroadmap.domain.entity.Solved;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SolvedCustomRepository {
    Page<Solved> dynamicSearching(String difficulty, String order, Boolean problemSolved, Pageable pageable);
}
