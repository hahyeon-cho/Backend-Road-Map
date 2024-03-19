package ncnk.make.backendroadmap.domain.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.aop.time.callback.TraceTemplate;
import ncnk.make.backendroadmap.domain.entity.CodingTest;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.Solved;
import ncnk.make.backendroadmap.domain.exception.ResourceNotFoundException;
import ncnk.make.backendroadmap.domain.repository.Solved.SolvedRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 테스트 풀이 여부 Service (BIZ 로직)
 */
@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class SolvedService {
    private final SolvedRepository solvedRepository;
    private final TraceTemplate template;

    //코딩 테스트 풀이 여부에 따라 포인트 더하는 로직
    @Transactional
    public Optional<Solved> solvedProblem(CodingTest codingTest, Member member) {
        return Optional.ofNullable(template.execute("SolvedService.solvedProblem()", () -> {
            // 푼 문제 검색
            Solved solved = solvedRepository.findSolvedByCodingTestAndMember(codingTest, member)
                    .orElseThrow(() -> new ResourceNotFoundException());

            // 푼 문제 풀이 여부 true로 변경
            solved.solveProblem();

            //푼 문제 상/중/하 레벨에 따라 멤버 정보 업데이트
            member.updateSolvedProblemsCount(codingTest.getProblemLevel());

            // 푼 문제에 대한 포인트 적립
            String problemLevel = solved.getCodingTest().getProblemLevel();
            solved.getMember().calculatePoint(problemLevel);
            return solved;
        }));
    }

    // 한번 시도한 문제의 경우 solved 테이블에 컬럼을 추가한다.
    @Transactional
    public Optional<Solved> recordAttemptedProblem(CodingTest codingTest, Member member, boolean isCorrect) {
        if (solvedRepository.existsByCodingTestAndMember(codingTest, member)) {
            return Optional.empty();
        }
        return Optional.ofNullable(template.execute("SolvedService.save()", () -> {
            //TODO: problemPath 바꾸기
            Solved solved = Solved.createSolved(codingTest, member, isCorrect, "solvedService-recordAttemptedProblem");
            solvedRepository.save(solved);
            return solved;
        }));
    }

    //마이페이지(MyTest) 검색 기능
    public Page<Solved> dynamicSearching(String difficulty, String order, Boolean problemSolved, Pageable pageable) {
        return solvedRepository.dynamicSearching(difficulty, order, problemSolved, pageable);
    }
}
