package ncnk.make.backendroadmap.init;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ncnk.make.backendroadmap.domain.entity.CodingTest;
import ncnk.make.backendroadmap.domain.entity.Quiz;

@NoArgsConstructor
@Getter
public class Quizs {
    List<Quiz> quizs = new ArrayList<>();

    List<CodingTest> codingTests = new ArrayList<>();
}