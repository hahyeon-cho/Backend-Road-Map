package ncnk.make.backendroadmap.domain.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProblemInfo {
    private String slug;
    private String title;
    private int level;
    private double correctRate;
    private String contents;
    private List<String> images = new ArrayList<>();
    private List<CodingTestAnswerDTO> exampleResults = new ArrayList<>();
    private List<String> tags = new ArrayList<>();

    private ProblemInfo() {
    }

    public static ProblemInfo createProblemInfo(){
        return new ProblemInfo();
    }

    public void updateInit(String slug, String title, int level){
        this.slug = slug;
        this.title = title;
        this.level = level;
    }

}


