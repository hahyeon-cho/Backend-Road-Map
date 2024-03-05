package ncnk.make.backendroadmap.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.aop.time.callback.TraceTemplate;
import ncnk.make.backendroadmap.domain.entity.CodingTest;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.Solved;
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
    public void calculatePoint(Member member, CodingTest codingTest) {
//        List<Solved> solvedByMemberAndCodingTest = solvedRepository.findSolvedByMemberAndCodingTest(member, codingTest);
//        for (Solved solved : solvedByMemberAndCodingTest) {
//            if (solved.getProblemSolved()) {
//                member.calculatePoint(
//                        codingTest.getProblemLevel()); //TODO: 리트코드 api의 레벨 정보가 hard/mid/easy 인지 상/중/하... 어떻게 들어오냐에 따라 변경해야함!
//            }
//        }
    }

    //마이페이지(MyTest) 검색 기능
    public Page<Solved> dynamicSearching(String difficulty, String order, Boolean problemSolved, Pageable pageable) {
        return solvedRepository.dynamicSearching(difficulty, order, problemSolved, pageable);
    }
}
