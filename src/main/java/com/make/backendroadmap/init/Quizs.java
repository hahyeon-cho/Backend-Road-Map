package com.make.backendroadmap.init;

import com.make.backendroadmap.domain.entity.CodingTest;
import com.make.backendroadmap.domain.entity.Quiz;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Quizs {
    List<Quiz> quizs = new ArrayList<>();

    List<CodingTest> codingTests = new ArrayList<>();
}
