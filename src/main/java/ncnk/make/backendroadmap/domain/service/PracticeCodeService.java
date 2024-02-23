package ncnk.make.backendroadmap.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.PracticeCode;
import ncnk.make.backendroadmap.domain.repository.PracticeCodeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class PracticeCodeService {
    private final PracticeCodeRepository practiceCodeRepository;

    public Page<PracticeCode> getPracticesByMember(Member member, Pageable pageable) {
        return practiceCodeRepository.findPracticeCodesByMember(member, pageable);
    }

    @Transactional
    public void save(String fileName, String filePath, String extension, Member member) {
        PracticeCode practiceCode = PracticeCode.createPracticeCode(fileName, String.valueOf(filePath), extension,
                member);
        practiceCodeRepository.save(practiceCode);
    }
}
