package ncnk.make.backendroadmap.domain.utils.wrapper;

import java.util.List;
import lombok.Getter;

@Getter
public class CodingTestProblem {
    private String problemTitle;
    private String problemSlug;
    private String problemLevel;
    private double problemAccuracy;
    private String problemContents;
    private List<String> problemImages;
    private List<CodingTestAnswer> exInputOutput;
    private List<String> problemTopics;

    private CodingTestProblem(String problemTitle, String problemSlug, String problemLevel, double problemAccuracy,
                              String problemContents, List<String> problemImages, List<CodingTestAnswer> exInputOutput,
                              List<String> problemTopics) {
        this.problemTitle = problemTitle;
        this.problemSlug = problemSlug;
        this.problemLevel = problemLevel;
        this.problemAccuracy = problemAccuracy;
        this.problemContents = problemContents;
        this.problemImages = problemImages;
        this.exInputOutput = exInputOutput;
        this.problemTopics = problemTopics;
    }

    public static CodingTestProblem createProblemInfo(String problemTitle, String problemSlug, String problemLevel,
                                                      double problemAccuracy,
                                                      String problemContents, List<String> problemImages,
                                                      List<CodingTestAnswer> exInputOutput,
                                                      List<String> problemTopics) {
        return new CodingTestProblem(problemTitle, problemSlug, problemLevel, problemAccuracy, problemContents,
                problemImages, exInputOutput, problemTopics);
    }
}


