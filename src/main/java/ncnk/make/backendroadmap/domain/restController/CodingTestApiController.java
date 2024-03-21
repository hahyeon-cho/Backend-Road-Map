package ncnk.make.backendroadmap.domain.restController;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.CodingTest;
import ncnk.make.backendroadmap.domain.restController.dto.CodingTest.CodingTestResponseDto;
import ncnk.make.backendroadmap.domain.restController.dto.CodingTest.ProblemPageResponseDto;
import ncnk.make.backendroadmap.domain.service.CodingTestService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 코딩 테스트 RestController (json)
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/codingtest")
public class CodingTestApiController {
    private final CodingTestService codingTestService;

    // 문제 리스트 페이지
    // http://localhost:8080/api/codingtest?page=0&size=100&problemLevel=&problemAccuracy=&status=unsolved
    //     속성값 problemLevel: Hard/Middle/Easy problemAccuracy: asc/desc status: solved/unsolved/incorrect
    @GetMapping("")
    public CodingTestPage getProblemListPage(
            @RequestParam(value = "problemLevel", required = false) String problemLevel,
            @RequestParam(value = "problemAccuracy", required = false) String problemAccuracy,
            @RequestParam(value = "status", required = false) String status,
            @PageableDefault(size = 5, direction = Direction.ASC) Pageable pageable) {

        List<CodingTest> codingTests = codingTestService.dynamicSearching(problemLevel, problemAccuracy, status,
                pageable).getContent();

        List<ProblemPageResponseDto> codingTestResponseDtos = new ArrayList<>();

        if (!codingTests.isEmpty()) {
            for (CodingTest codingTest : codingTests) {
                codingTestResponseDtos.add(ProblemPageResponseDto.createCodingTestResponse(codingTest));
            }
        }
        return new CodingTestPage(pageable.getPageSize(), codingTestResponseDtos);
    }

    // 알고리즘 문제 풀기 페이지
    @GetMapping("/{id}")
    public ProblemPage problemPage(@PathVariable Long id) {
        CodingTest codingTest = codingTestService.findCodingTestById(id);
        CodingTestResponseDto codingTestResponse = CodingTestResponseDto.createCodingTestResponse(codingTest);
        return new ProblemPage(codingTestResponse);
    }

    @AllArgsConstructor
    @Getter
    static class CodingTestPage<T> {
        private int pageSize;
        private T data;
    }

    @AllArgsConstructor
    @Getter
    static class ProblemPage<T> {
        private T data;
    }
}
