package ncnk.make.backendroadmap.init;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ncnk.make.backendroadmap.domain.entity.CodingTest;
import ncnk.make.backendroadmap.domain.entity.Quiz;

@Getter
@NoArgsConstructor
public class Quizzes {

    List<Quiz> quizzes = new ArrayList<>();

    List<CodingTest> codingTests = new ArrayList<>();
}
