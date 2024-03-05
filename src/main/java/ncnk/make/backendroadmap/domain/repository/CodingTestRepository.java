package ncnk.make.backendroadmap.domain.repository;

import ncnk.make.backendroadmap.domain.entity.CodingTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodingTestRepository extends JpaRepository<CodingTest, Long> {

}