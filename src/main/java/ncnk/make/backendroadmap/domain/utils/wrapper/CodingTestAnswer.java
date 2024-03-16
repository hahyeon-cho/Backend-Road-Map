package ncnk.make.backendroadmap.domain.utils.wrapper;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CodingTestAnswer {
    @NotBlank
    private String input;

    @NotBlank
    private String output;

    private CodingTestAnswer(String input, String output) {
        this.input = input;
        this.output = output;
    }

    public static CodingTestAnswer createCodingTestAnswer(String input, String output) {
        return new CodingTestAnswer(input, output);
    }
}