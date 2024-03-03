package ncnk.make.backendroadmap.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.aop.time.callback.TraceTemplate;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.PracticeCode;
import ncnk.make.backendroadmap.domain.repository.PracticeCodeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 웹 컴파일러 Service (BIZ 로직)
 */
@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class PracticeCodeService {
    private final PracticeCodeRepository practiceCodeRepository;

    private final TraceTemplate template;

    //회원 정보를 이용해 웹 컴파일러 정보를 Page 반환
    public Page<PracticeCode> getPracticesByMember(Member member, Pageable pageable) {
        return practiceCodeRepository.findPracticeCodesByMember(member, pageable);
    }

    //웹 컴파일러 다운로드
    @Transactional
    public void save(String fileName, String filePath, String extension, Member member) {
        template.execute("PracticeCodeService.save()", () -> {
            PracticeCode practiceCode = PracticeCode.createPracticeCode(fileName, String.valueOf(filePath), extension,
                    member);
            practiceCodeRepository.save(practiceCode);
            return null;
        });
    }
}
