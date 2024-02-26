package ncnk.make.backendroadmap.domain.repository.Solved;

import ncnk.make.backendroadmap.domain.entity.Solved;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolvedRepository extends JpaRepository<Solved, Long>, SolvedCustomRepository {
}
