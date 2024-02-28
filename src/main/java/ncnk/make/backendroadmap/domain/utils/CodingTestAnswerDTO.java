package ncnk.make.backendroadmap.domain.utils;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CodingTestAnswerDTO {
    @NotBlank
    private String input;

    @NotBlank
    private String output;

    public void setExample(String in, String out){
        input = in;
        output = out;
    }
}
