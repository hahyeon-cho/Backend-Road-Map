package ncnk.make.backendroadmap.domain.entity;

import lombok.Getter;
import ncnk.make.backendroadmap.domain.exception.ResourceNotFoundException;

@Getter
public enum Problem {
    HARD("상", 7),
    MIDDLE("중", 5),
    EASY("하", 3);

    private final String problemLevel;
    private final int point;

    Problem(String problemLevel, int point) {
        this.problemLevel = problemLevel;
        this.point = point;
    }

    public static Problem getProblem(String problemLevel) {
        for (Problem problem : Problem.values()) {
            if (problem.getProblemLevel().equals(problemLevel)) {
                return problem;
            }
        }
        throw new ResourceNotFoundException();
    }

}
