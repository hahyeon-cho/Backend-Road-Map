package ncnk.make.backendroadmap.domain.entity;

import lombok.Getter;
import ncnk.make.backendroadmap.domain.exception.ResourceNotFoundException;

/**
 * 코딩 테스트 (난이도, 포인트)
 */
@Getter
public enum Problem {
    HARD("Hard", 7),
    NORMAL("Normal", 5),
    EASY("Easy", 3);

    private final String problemLevel;
    private final int point;

    // 생성자
    Problem(String problemLevel, int point) {
        this.problemLevel = problemLevel;
        this.point = point;
    }

    // 인자 값(상, 중, 하)와 일치하는 정보 반환
    public static Problem getProblem(String problemLevel) {
        for (Problem problem : Problem.values()) {
            if (problem.getProblemLevel().equals(problemLevel)) {
                return problem;
            }
        }
        throw new ResourceNotFoundException();
    }
}