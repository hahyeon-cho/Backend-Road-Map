package ncnk.make.backendroadmap.crawling.leetcode;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ExampleResult {
    @NotBlank
    private String input;

    @NotBlank
    private String output;

    public void setExample(String in, String out){
        input = in;
        output = out;
    }
}
