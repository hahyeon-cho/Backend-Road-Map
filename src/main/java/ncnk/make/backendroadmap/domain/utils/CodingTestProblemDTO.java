package ncnk.make.backendroadmap.domain.utils;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CodingTestProblemDTO {
    private String problemTitle;
    private String problemSlug;
    private String problemLevel;
    private double problemAccuracy;
    private String problemContents;
    private List<String> problemImages = new ArrayList<>();
    private List<CodingTestAnswerDTO> exInputOutput = new ArrayList<>();
    private List<String> problemTopics = new ArrayList<>();

    private CodingTestProblemDTO() {
    }

    public static CodingTestProblemDTO createProblemInfo(){
        return new CodingTestProblemDTO();
    }

    public void updateInit(String title, String slug,
                           String level, double correctRate,
                           String contents,
                           List<String> imagePaths,
                           List<CodingTestAnswerDTO> exInputOutput,
                           List<String> topics
                           ){
        this.problemTitle = title;
        this.problemSlug = slug;
        this.problemLevel = level;
        this.problemAccuracy = correctRate;
        this.problemContents = contents;
        this.problemImages = imagePaths;
        this.exInputOutput = exInputOutput;
        this.problemTopics = topics;
    }

}


